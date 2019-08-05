package com.springdisplay.springwebdisplay.model;

public class Pets {

	public String _id;
	public String name;
	public String species;
	public String breed;

	// Constructors
	public Pets() {}

	public Pets(String _id, String name, String species, String breed) {
		this._id = _id;
		this.name = name;
		this.species = species;
		this.breed = breed;
	}

	// ObjectId needs to be converted to string
	public String get_id() { return _id; }
	public void set_id(String _id) { this._id = _id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getSpecies() { return species; }
	public void setSpecies(String species) { this.species = species; }

	public String getBreed() { return breed; }
	public void setBreed(String breed) { this.breed = breed; }

	@Override
	public String toString() {
		return "Pets [_id=" + _id + ", name=" + name + ", species=" + species + ", breed=" + breed + "]";
	}

}