package com.devsuperior.dslearnbds.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_offer")
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
