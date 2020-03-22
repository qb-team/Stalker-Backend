package it.qbteam.file;
import org.springframework.data.annotation.Id;

public class Moviment {

    @Id
    private long id;
    private String type;
}