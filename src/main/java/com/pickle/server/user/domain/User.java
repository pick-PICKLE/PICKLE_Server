
package com.pickle.server.user.domain;



import com.pickle.server.common.Timestamped;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
public class User extends Timestamped {

    @Id
    private String id;

}
