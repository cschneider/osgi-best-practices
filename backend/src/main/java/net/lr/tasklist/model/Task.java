package net.lr.tasklist.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * For simplification of the code this class acts as 
 * internal service model class as well as jax-rs resource DTO.
 * In a real application you might want to split these.
 */
@XmlRootElement
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    Integer id;
    String title;
    String description;
    Date dueDate;
    boolean finished;
}
