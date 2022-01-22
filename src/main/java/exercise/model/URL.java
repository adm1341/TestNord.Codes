package exercise.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "url")
public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String fullUrl;

    private String shortUrl;

    private Integer numberClick;

    private Integer timeLive;

    private Date dateCreate;

}
