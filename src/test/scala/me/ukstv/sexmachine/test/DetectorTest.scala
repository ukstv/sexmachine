package me.ukstv.sexmachine.test

import org.specs2.mutable._
import me.ukstv.sexmachine._

object DetectorTest extends Specification {
  
  "Detector" should {
    "return optional sex" in {
      Detector.getGenderOpt("Ábel") mustEqual Some(Sex.Male)
    }
    
    "return sex" in {
      Detector.getGender("Aatto") mustEqual Sex.Male
    }
  }
 
}
