package de.fhbingen.epro.sql;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
public class AbstractEntity {
	
	@Id
	@SequenceGenerator(name= "seqGenerator", sequenceName = "DICTIONARY_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGenerator")
	private Long id;

	public Long getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof AbstractEntity) if(this.id == ((AbstractEntity) obj).getId()) return true;
		return false;
	}
}
