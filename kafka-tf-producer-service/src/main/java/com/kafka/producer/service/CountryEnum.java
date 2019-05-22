package com.kafka.producer.service;

import java.util.ArrayList;
import java.util.List;

public enum CountryEnum {

	CHENNAI("India", "tamil nadu", "Chennai", 101.1), VELLOR("India", "tamil nadu", "vellor", 105.0),
	KOVAI("India", "tamil nadu", "kovai", 92.1), TUTICORIN("India", "tamil nadu", "tuticorin", 105.0),
	MADURAI("India", "tamil nadu", "madurai", 101.1), SALEM("India", "tamil nadu", "salem", 105.0),
	Namakkal("India", "tamil nadu", "Namakkal", 101.1), Pammal("India", "tamil nadu", "pammal", 115.8),
	tambaram("India", "tamil nadu", "tambaram", 103.1), medavakkam("India", "tamil nadu", "medavakkam", 96.0),
	mouniya("SRILANKA", "tamil", "mouniya", 103.1), mullivaykal("SRILANKA", "tamil", "mullivaykal", 116.8),
	Jayawardenepura("SRILANKA", "Kolumbu", "Jayawardenepura", 106.1),
	mattakalapu("SRILANKA", "kolumbu", "mattakalapu", 94.0);

	private String country;
	private String state;
	private String city;
	private Double temp;

	private CountryEnum(String country, String state, String city, Double temp) {
		this.country = country;
		this.state = state;
		this.city = city;
		this.temp = temp;
	}

	public String getCountry() {
		return country;
	}

	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}

	public Double getTemp() {
		return temp;
	}

	public static List<Weather> getCountryList() {
		List<Weather> weatherList = new ArrayList<Weather>();
		CountryEnum[] countries = CountryEnum.values();
		for (CountryEnum country : countries) {
			Weather weather = new Weather();
			weather.setCountry(country.getCountry());
			weather.setState(country.getState());
			weather.setCity(country.getCity());
			weather.setTemp(country.getTemp());
			weatherList.add(weather);
		}
		return weatherList;
	}

}
