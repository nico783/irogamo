package myapp.repository;

import myapp.featurestatistic.StatisticDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@Repository
public class StatisticRepository {

    @PersistenceContext
    private EntityManager em;

    public List<StatisticDto> findStatistics(String userName, LocalDate min, LocalDate max) {

        String query = "select new myapp.featurestatistic.StatisticDto(" +
                " u.username, " +
                " d.creation, " +
                " t.category, " +
                " t.project, " +
                " t.label, " +
                " d.businessUnit, " +
                " t.codeOrigami, " +
                " d.description, " +
                " d.duration)" +
                " from WorkDone wd " +
                " join wd.durations d " +
                " join wd.task t " +
                " join wd.irogamoUser u " +
                " where 1=1 " +
                " and d.creation >= :minim " +
                " and d.creation <= :maxim ";

        if(userName != null && !"All".equals(userName)) {
            query += " and u.username = :username ";
        }

        query += " order by u.username, d.creation ";

        TypedQuery<StatisticDto> typedQuery = em.createQuery(query, StatisticDto.class);

        if(userName != null && !"All".equals(userName)) {
            typedQuery.setParameter("username", userName);
        }

        typedQuery.setParameter("minim", min);
        typedQuery.setParameter("maxim", max);

        return typedQuery.getResultList();
    }

}
