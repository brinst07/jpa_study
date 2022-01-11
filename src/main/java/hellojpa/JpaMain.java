package hellojpa;

import hellojpa.domain.Order;
import hellojpa.domain.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        //애플리케이 로딩시점에 하나만 만든다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //DB 접속하는 일괄적인 행위를 할때마다 entityManager를 만들어야함
        //쉽게말해서 DB connection을 받았다고 생각하면
        EntityManager entityManager = emf.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        try {
            //비영속
            Member member = new Member();
            member.setId(3L);
            member.setName("test");

            //영속
            //DB에 저장되는게 아닌, 영속성 컨텍스트에 저장되는 것임
            entityManager.persist(member);

            //준영속
            //영속성컨텍스트에서 삭제
            entityManager.detach(member);

            //삭제
            //DB에서 삭제 요청 의미
            entityManager.remove(member);


            Member findMember = entityManager.find(Member.class, 1L);

            findMember.setName("helloD");


            transaction.commit();

            Order order = new Order();
            order.addOrderItem(new OrderItem());

        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        emf.close();
    }
}
