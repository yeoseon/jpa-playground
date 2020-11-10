package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", // 맵핑할 데이터베이스 시퀀스 이름
        initialValue =  1, allocationSize = 1)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                generator = "MEMBER_SEQ_GENERATOR")
    private Long id;
    @Column(name = "name", nullable = false, columnDefinition = "varchar(100) default 'EMPTY'")
    private String username;
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
}
