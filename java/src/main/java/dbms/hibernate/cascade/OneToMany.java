package dbms.hibernate.cascade;

import dbms.hibernate.HibernateUtils;
import dbms.hibernate.SessionFactoryBuilder;
import dbms.hibernate.TransactionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static dbms.hibernate.HibernateUtils.throwNotFound;
import static dbms.hibernate.TransactionUtils.commit;
import static dbms.hibernate.TransactionUtils.persist;
import static dbms.hibernate.TransactionUtils.operate;

public interface OneToMany {

    String JOIN = "select ts.id team_id, ts.name, ps.id player_id, ps.review from teams ts left join players ps on ts.id = ps.team_id";

    static void main(String[] args) {
        SessionFactory sessionFactory =
            new SessionFactoryBuilder()
                .resourceName("/META-INF/hibernate-postgresql-example.cfg.xml")
                .entitiesClasses(Team.class, Player.class)
                .build();

        Session session = sessionFactory.openSession();

        cascadePersist(session);
        cascadeMerge(session);
        cascadeOrphanRemove(session);
        cascadeDelete(session);
    }

    private static void cascadePersist(Session session) {
        TransactionUtils.persist(session,
            new Team().setName("Hibernate Team")
                .addPlayers(
                    new Player().setReview("Good player!"),
                    new Player().setReview("Nice player!")
                ),

            new Team().setName("Eclipse Team")
                .addPlayers(
                    new Player().setReview("Average player..."),
                    new Player().setReview("Not good player!")
                )
        );

        HibernateUtils.printNativeSelect(session, "cascadePersist", JOIN);
    }

    private static void cascadeMerge(Session session) {
        TransactionUtils.commit(session, s -> {
            int id = 1;
            Class<Team> teamClass = Team.class;
            HibernateUtils.find(session, teamClass, id).ifPresentOrElse(team -> {
                    team.setName("Hibernate Dream Team")
                        .getPlayers()
                        .stream()
                        .filter(player -> player.getReview().toLowerCase().contains("nice"))
                        .findAny()
                        .ifPresent(player -> player.setReview("Keep up the good work!"));

                    TransactionUtils.operate(session, Session::merge, team);
                },
                HibernateUtils.throwNotFound(teamClass.getSimpleName(), id));
        });
        HibernateUtils.printNativeSelect(session, "cascadeMerge", JOIN);
    }

    private static void cascadeOrphanRemove(Session session) {
        TransactionUtils.commit(session, s -> {
            int id = 1;
            Class<Team> teamClass = Team.class;
            HibernateUtils.find(s, teamClass, id).ifPresentOrElse(
                team -> team.removePlayer(team.getPlayers().get(0)),
                HibernateUtils.throwNotFound(teamClass.getSimpleName(), id)
            );
        });

        HibernateUtils.printNativeSelect(session, "cascadeOrphanRemove", JOIN);
    }

    private static void cascadeDelete(Session session) {
        int id = 4;
        Class<Team> teamClass = Team.class;

        HibernateUtils.find(session, teamClass, id)
            .ifPresentOrElse(
                session::delete,
                HibernateUtils.throwNotFound(teamClass.getSimpleName(), id)
            );

        HibernateUtils.printNativeSelect(session, "cascadeDelete", JOIN);
    }
}
