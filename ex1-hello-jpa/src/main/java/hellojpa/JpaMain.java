package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.swing.*;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");     // emf를 만드는 순간, DB와 연결도 되고 각종 설정도 다된다. **로딩 시점에 딱 하나만 만들어야 한다.**
        EntityManager em = emf.createEntityManager();    // EntityManager 를 꺼내서 사용을 위한 Code를 작성한다. Query를 수행할 때마다 하나씩 만들어졌다가 close 되어야 한다.

        // JPA는 Transaction 설정이 가장 중요하므로 아래와 같이 Transaction을 얻어서 시작처리 해야한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member member = new Member();

        member.setId(1L);
        member.setName("HelloA");

        em.persist(member);

        tx.commit();

        // 사용 후 둘다 Close 해줘야 한다.
        em.close();
        emf.close();
    }
}
