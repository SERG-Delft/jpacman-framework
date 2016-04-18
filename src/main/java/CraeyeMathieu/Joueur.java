package CraeyeMathieu;

import java.awt.List;
import java.util.ArrayList;

public class Joueur {

	private int numero;
	private int score;
	private String name;
	private ArrayList<Joueur> listJoueur;
	private int NbrJoueur;
	
	public Joueur(String name_,int numero_,int score_){
		
		name=name_;
		numero=numero_;
		score=score_;
		
	}
	public Joueur(){}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public ArrayList<Joueur> getListJoueur() {
		return listJoueur;
	}
	public void setListJoueur(ArrayList<Joueur> listJoueur) {
		this.listJoueur = listJoueur;
	}
	public void SetNbrJoueur(int n)
	{
		this.NbrJoueur=n;
	}
	public int getNbrJoueur()
	{
		return NbrJoueur;
	}
	public void addJoueur(Joueur j)
	{
		listJoueur.add(j);
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
