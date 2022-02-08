package dbms.hibernate.cascade;

import dbms.hibernate.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static dbms.hibernate.HibernateUtils.find;
import static dbms.hibernate.HibernateUtils.printNativeJoin;
import static dbms.hibernate.HibernateUtils.throwNotFound;
import static dbms.hibernate.TransactionUtils.commit;
import static dbms.hibernate.TransactionUtils.persist;
import static dbms.hibernate.TransactionUtils.transact;

public interface OneToMany {

    String JOIN = "select ts.id team_id, ts.name, ps.id player_id, ps.review from teams ts left join players ps on ts.id = ps.team_id";

    static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory(
            "/META-INF/hibernate-postgresql-example.cfg.xml",
            Team.class,
            Player.class);

        Session session = sessionFactory.openSession();

        cascadePersist(session);
        cascadeMerge(session);
        cascadeOrphanRemove(session);
        cascadeDelete(session);
    }

    private static void cascadePersist(Session session) {
        persist(session,
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

        printNativeJoin(session, "cascadePersist", JOIN);
    }

    private static void cascadeMerge(Session session) {
        commit(session, s -> {
            int id = 1;
            Class<Team> teamClass = Team.class;
            find(session, teamClass, id).ifPresentOrElse(team -> {
                    team.setName("Hibernate Dream Team")
                        .getPlayers()
                        .stream()
                        .filter(player -> player.getReview().toLowerCase().contains("nice"))
                        .findAny()
                        .ifPresent(player -> player.setReview("Keep up the good work!"));

                    transact(session, Session::merge, team);
                },
                throwNotFound(teamClass.getSimpleName(), id));
        });
        printNativeJoin(session, "cascadeMerge", JOIN);
    }

    private static void cascadeOrphanRemove(Session session) {
        commit(session, s -> {
            int id = 1;
            Class<Team> teamClass = Team.class;
            find(s, teamClass, id).ifPresentOrElse(
                team -> team.removePlayer(team.getPlayers().get(0)),
                throwNotFound(teamClass.getSimpleName(), id)
            );
        });

        printNativeJoin(session, "cascadeOrphanRemove", JOIN);
    }

    private static void cascadeDelete(Session session) {
        int id = 4;
        Class<Team> teamClass = Team.class;

        find(session, teamClass, id)
            .ifPresentOrElse(
                session::delete,
                throwNotFound(teamClass.getSimpleName(), id)
            );

        printNativeJoin(session, "cascadeDelete", JOIN);
    }
}
