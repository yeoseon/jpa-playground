package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");     // emf를 만드는 순간, DB와 연결도 되고 각종 설정도 다된다.

        EntityManager entityManager = emf.createEntityManager();    // EntityManager 를 꺼내서 사용을 위한 Code를 작성한다.

        // 사용 후 둘다 Close 해줘야 한다.
        entityManager.close();
        emf.close();
    }
}
