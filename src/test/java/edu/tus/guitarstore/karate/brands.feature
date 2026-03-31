Feature: Brand API (Karate)

Background:
  * url 'http://localhost:8080'
  * path '/api/guitarstore/v1/brands'
  * def suffix = java.util.UUID.randomUUID() + ''
  * def brandName = 'KarateBrand-' + suffix

Scenario: Happy path - create a brand then fetch it
  Given request { name: '#(brandName)', country: 'USA' }
  When method post
  Then status 201

  Given path '/api/guitarstore/v1/brands', brandName
  When method get
  Then status 200
  And match response.name == brandName
  And match response.country == 'USA'
 
Scenario: Validation failure - missing country should return 400
  * def badName = 'BadBrand-' + suffix
  Given request { name: '#(badName)' }
  When method post
  Then status 400
  And match response.country == 'Country cannot be null or empty'

Scenario: Not found - fetching unknown brand should return 404
  Given path '/api/guitarstore/v1/brands', 'DoesNotExist-' + suffix
  When method get
  Then status 404
  And match response.apiPath contains '/api/guitarstore/v1/brands'
  And match response.errorMessage == '#present'
  And match response.errorTime == '#present'
  
Scenario: Create then delete brand, then fetch returns 404
  * def name = 'DeleteBrand-' + suffix

  Given request { name: '#(name)', country: 'USA' }
  When method post
  Then status 201

  Given path '/api/guitarstore/v1/brands', name
  When method delete
  Then status 200

  Given path '/api/guitarstore/v1/brands', name
  When method get
  Then status 404
  
  Then status 404
  And match response.apiPath contains '/api/guitarstore/v1/brands'
  And match response.errorMessage == '#present'
  And match response.errorTime == '#present'