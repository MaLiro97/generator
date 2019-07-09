package ma.zs.generator.bean;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CommandeItem {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private Produit produit;
	private BigDecimal prix;
	private BigDecimal qte;
	@ManyToOne
	private Commande commande;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
		CommandeItem other = (CommandeItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public CommandeItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CommandeItem(Long id) {
		super();
		this.id = id;
	}
	public Produit getProduit() {
		return produit;
	}
	public void setProduit(Produit produit) {
		this.produit = produit;
	}
	public BigDecimal getPrix() {
		return prix;
	}
	public void setPrix(BigDecimal prix) {
		this.prix = prix;
	}
	public BigDecimal getQte() {
		return qte;
	}
	public void setQte(BigDecimal qte) {
		this.qte = qte;
	}
	@Override
	public String toString() {
		return "CommandeItem [id=" + id + ", produit=" + produit.getLibelle() + ", prix=" + prix + ", qte=" + qte + "]";
	}
	
	
	
	
	
	
}
