package com.georgidinov.sfgrecepies.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //== fields ==
    @OneToOne
    private Recipe recipe;

    @Lob
    private String recipeNotes;

}//end of class Notes
