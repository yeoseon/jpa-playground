package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
//@SequenceGenerator(
//        name = "MEMBER_SEQ_GENERATOR",
//        sequenceName = "MEMBER_SEQ", // 맵핑할 데이터베이스 시퀀스 이름
//        initialValue =  1, allocationSize = 1)
@TableGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
                generator = "MEMBER_SEQ_GENERATOR")
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "name", nullable = false, columnDefinition = "varchar(100) default 'EMPTY'")
    private String username;
//    private Long teamId;                // 데이터 중심으로 설계한 좋지 않은 설계

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")       // Member의 객체 Team의 참조와, Member 테이블의 Team_ID FK를 서로 맵핑시킨다.
    private Team team;

    private Integer age;
    @Enumerated(EnumType.STRING)        // DB에는 없는 Enum 타입 사용을 위해 해당 어노테이션을 붙힌다.
    private RoleType roleType;
    @Temporal(TemporalType.TIMESTAMP)   // Date, Time, TIMESTAMP
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Lob                                // DLOB, CLOB 사용을 위해 설정 (@Lob에 String 타입이면 CLOB으로 사용된다.)
    private String description;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Member() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
