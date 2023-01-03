
package com.pickle.pickle.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "user")
public class User extends Timestamped {

    @Id
    private String id;

}
