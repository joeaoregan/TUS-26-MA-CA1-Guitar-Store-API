package edu.tus.guitarstore.karate;

import com.intuit.karate.junit5.Karate;

class BrandKarateTest {

	@Karate.Test
	Karate testBrands() {
		return Karate.run("brands").relativeTo(getClass());
	}
}