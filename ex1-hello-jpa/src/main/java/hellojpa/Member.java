package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Member {

    @Id
    private Long id;
    @Column(name = "name")
    private String username;
    private Integer age;
    @Enumerated(EnumType.STRING)        // DB에는 없는 Enum 타입 사용을 위해 해당 어노테이션을 붙힌다.
    private RoleType roleType;
    @Temporal(TemporalType.TIMESTAMP)   // Date, Time, TIMESTAMP
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Lob                                // DLOB, CLOB 사용을 위해 설정
    private String description;

    public Member() {}
}
