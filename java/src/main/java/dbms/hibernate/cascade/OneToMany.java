package dbms.hibernate.cascade;

import dbms.hibernate.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static dbms.hibernate.TransactionUtils.commit;
import static dbms.hibernate.TransactionUtils.save;

public interface OneToMany {
    static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory("/META-INF/hibernate-postgresql-example.cfg.xml", Team.class, Player.class);

        Session session = sessionFactory.openSession();

        cascadePersist(session);
        cascadeMerge(session);
        cascadeDelete(session);
        cascadeOrphanRemove(session);
    }

    private static void cascadeOrphanRemove(Session session) {
        commit(session, s -> {
            Team team = (Team) s
                .createQuery("select t from Team t join fetch t.players where t.id = :id")
                .setParameter("id", 1L)
                .uniqueResult();

            team.removePlayer(team.getPlayers().get(0));
        });
    }

    private static void cascadeDelete(Session session) {
        Team team = new Team();
        save(session, team);
        commit(session, s -> {
            s.delete(team);
        });
    }

    private static void cascadeMerge(Session session) {
        Team team = new Team();
        team.setName("Hibernate Master Class Training Material");

        team.getPlayers()
            .stream()
            .filter(player -> player.getReview().toLowerCase()
                .contains("nice"))
            .findAny()
            .ifPresent(player ->
                player.setReview("Keep up the good work!")
            );

        commit(session, s -> {
            s.merge(team);
        });
    }

    private static void cascadePersist(Session session) {
        Team team = new Team();
        team.setName("Hibernate Master Class");

        Player player1 = new Player();
        player1.setReview("Good team!");
        Player player2 = new Player();
        player2.setReview("Nice team!");

        team.addPlayer(player1);
        team.addPlayer(player2);

        session.persist(team);
    }
}
