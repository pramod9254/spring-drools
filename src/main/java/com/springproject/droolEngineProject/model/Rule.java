package com.springproject.droolEngineProject.model;

//import javax.persistence.*;
import jakarta.persistence.*;

@Entity
@Table(name = "rule_table")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(name = "ifcondition")
    private String ifcondition;
    
    @Column(name = "thencondition")
    private String thencondition;
    
    @Column(name = "version")
    private int version;
    
    @Column(name = "position")
    private String position;
    
    @Column(name = "description")
    private String description;

    // Explicit getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIfcondition() {
        return ifcondition;
    }

    public void setIfcondition(String ifcondition) {
        this.ifcondition = ifcondition;
    }

    public String getThencondition() {
        return thencondition;
    }

    public void setThencondition(String thencondition) {
        this.thencondition = thencondition;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
