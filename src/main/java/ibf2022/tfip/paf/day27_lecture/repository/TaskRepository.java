package ibf2022.tfip.paf.day27_lecture.repository;


import java.sql.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Repository
public class TaskRepository {
    private static final String C_ACTIVITIES = "activities";
    @Autowired
    private MongoTemplate template;

    /* 
     * db.activies.insertOne({
     *  task: 'abc',
     *  activity: '
     * })
     */
    public Document insertTask(jakarta.json.JsonObject task){
        Document toInsert = Document.parse(task.toString());
        toInsert.put("dueDate", Date.valueOf(toInsert.getString("dueDate")));

        return template.insert(toInsert,C_ACTIVITIES);
    }

    /* 
     * db.activities.find({
     *      task: {$exists: false}
     * })
     */
    public void findWithoutTask(){
        Criteria critera = Criteria.where("task");
        Query query = Query.query(critera);
        List<Document> results = template.find(query, Document.class, C_ACTIVITIES);

        System.out.printf(">>>", results);
    }

    /* 
     * db.activities.find({
     *      task: {$exists: false}
     * })
     * 
     * db.
     */
    public void deleteActivitiesWithoutTask(){
        Criteria criteria = Criteria.where("task")
            .exists(false);
        Query query = Query.query(criteria);

        DeleteResult deleted = template.remove(query,C_ACTIVITIES);

        System.out.printf(">>> delete count: %d\n", deleted.getDeletedCount());
        System.out.printf(">>> ack: %b\n", deleted.wasAcknowledged());
    }

    /* 
     * db.activities.updateMany(
     *      {priority: {gte:7}},
     *      {$set: { important: true}}
     * )
     */
    public void updateActivity(){
        Criteria critera = Criteria.where("priority")
            .gte(7);

        Query query = Query.query(critera);

        Update updateOps = new Update().set("important", true);

        UpdateResult results = template.updateMulti(query, updateOps, Document.class, C_ACTIVITIES);

        System.out.printf("matched: %d\n", results.getMatchedCount());
        System.out.printf("modified: %d\n", results.getModifiedCount());
        System.out.printf("ack: %b\n", results.wasAcknowledged());
    }
}
