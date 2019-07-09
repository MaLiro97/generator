package ma.zs.generator.bean;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Commande {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String reference;
	private BigDecimal total;
	@OneToMany(mappedBy="commande")
	private List<CommandeItem> commandeItems;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Commande other = (Commande) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Commande [id=" + id + ", reference=" + reference + ", total=" + total + "]";
	}
	public Commande() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Commande( String reference, BigDecimal total) {
		this.reference = reference;
		this.total = total;
	}
	public Commande(Long id) {
		super();
		this.id = id;
	}
	
	
	
	
	
}
