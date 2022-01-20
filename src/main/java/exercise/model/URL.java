package exercise.model;

import lombok.Getter;
import lombok.Setter;

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

}
