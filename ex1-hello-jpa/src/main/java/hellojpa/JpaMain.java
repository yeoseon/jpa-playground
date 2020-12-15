package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");     // emf를 만드는 순간, DB와 연결도 되고 각종 설정도 다된다. **로딩 시점에 딱 하나만 만들어야 한다.**
        EntityManager em = emf.createEntityManager();    // EntityManager 를 꺼내서 사용을 위한 Code를 작성한다. Query를 수행할 때마다 하나씩 만들어졌다가 close 되어야 한다.

        // JPA는 Transaction 설정이 가장 중요하므로 아래와 같이 Transaction을 얻어서 시작처리 해야한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            //저장
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();       // 비영속 상태 (객체 생성)
            member.setUsername("HelloJPA");
            member.setTeam(team);
            em.persist(member);                 // 영속 상태

            em.flush();     // 영속성 컨텍스트에 있는 쿼리를 미리 실행한다. (find 쿼리 확인 위해)
            em.clear();     // em 을 클리어 한다.

            Member findMember = em.find(Member.class, member.getId());      // em.persist를 통해 영속성 컨텍스트에 들어갔다. 그러므로 이때 find는 1차캐시에서 가져온다. 따라서 find 쿼리는 실행되지 않는다. DB에서 join해서 가져오는 것을 확인하고 싶은 경우, flush 및 clear를 이용해 미리 insert 쿼리 넣고, em을 clear한다.

            // **양방향 연관관계 실습 코드
            List<Member> members = findMember.getTeam().getMembers();
            for(Member m : members) {
                System.out.println("member = " + m.getUsername());
            }

            // **단반향 연관관계 실습 코드
//            Team findTeam = findMember.getTeam();
//            System.out.println("team:" + findTeam.getName());
//
//            // Team을 변경하고 싶을 때 (연관관계 수정)
//            Team newTeam = em.find(Team.class, 100L);
//            findMember.setTeam(newTeam);        //  FK가 업데이트 된다.

            // **영속상태 설명
//            // 영속 상태
//            // em안의 영속성 컨텍스트 내부에 들어가 관리된다.
//            // 사실은 이때 DB에 저장되는 것이 아니다.
//            System.out.println("------------- BEFORE --------");
//            em.persist(member); // 사실 여기에서는 Query가 나가지 않는다.
//            System.out.println("------------- AFTER --------");
//
//            // 이 또한 영속 상태
//            Member findMember = em.find(Member.class, 101L);
//            member.setName("AAAAA");  // 변경 감지가 되어 Update SQL이 쓰기 지연 SQL 저장소에 저장된다.
//
//            // 준영속 상태로 영속상태에서 벗어나게 했다.
//            // 영속상태로 더이상 관리하지 말라는 의미이므로, 이 관련된 모든게 영속성 컨텍스트에서 빠진다.
//            // 따라서 위에서 Setter를 썼더라도, 해당 SQL은 삭제되어 commit 시점에 update 처리되지 않는다.
//            em.detach(member);
//
//            // 조회용 SQL이 수행되지 않는다.
//            // 1차 캐시에서 조회해오기 때문
//            System.out.println(findMember.getId());
//            System.out.println(findMember.getName());
//
//            // 사실은 이 시점에 쿼리가 날라가는 것이다.
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();     // 꼭 닫아줘야 한다.
        }

        // 사용 후 둘다 Close 해줘야 한다.
        emf.close();
    }
}
