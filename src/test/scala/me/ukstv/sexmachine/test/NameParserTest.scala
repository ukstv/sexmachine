package me.ukstv.sexmachine.test

import org.specs2.mutable._
import me.ukstv.sexmachine._

object NameParserTest extends Specification {
  
  "NameParser" should {
    "parse comments" in {
      val p = new NameParser
      val testString = "# some words $\n# another $"
      p.parseAll(p.lines, testString) match {
        case p.Success(r, _) => r mustEqual Map()
        case x => failure(x.toString)
      }
    }
    
    "parse comments of all sharps" in {
      val p = new NameParser
      val testString = """##################################################################################    $
###  syntax of name list  ########################################################    $
##################################################################################    $"""
        
      p.parseAll(p.lines, testString) match {
        case p.Success(r, _) => r mustEqual Map()
        case x => failure(x.toString)
      }
    }
    
    "parse single male or female line" in {
        val p = new NameParser
        val testString = "M  Ådne                      +                 1                                      $"
        p.parseAll(p.lines, testString) match {
          case p.Success(r, _) => r mustEqual Map("Ådne" -> Sex.Male)
          case x => failure(x.toString)
        }
    }
    
    "parse several male or female lines" in {
        val testString = """M  Aad                                  4                                             $
M  Aadam                                          1                                   $
F  Aadje                                1                                             $
M  Ådne                      +                 1                                      $
M  Aadu                                           12                                  $"""
      
        val p = new NameParser
        p.parseAll(p.lines, testString) match {
          case p.Success(r, _) => r mustEqual Map("Aad" -> Sex.Male, "Aadam" -> Sex.Male, "Aadje" -> Sex.Female, "Ådne" -> Sex.Male, "Aadu" -> Sex.Male)
          case x => failure(x.toString)
        }
    }
    
    "parse several mostly* lines" in {
      val testString = """M  Aadu                                           12                                  $
?F Aaf                                  1                                             $
F  Aafke                                4                                             $
?  Aafke                                 1                                            $
F  Aafkea                                1                                            $"""
        
      val p = new NameParser
      p.parseAll(p.lines, testString) match {
        case p.Success(r, _) => r mustEqual Map("Aadu" -> Sex.Male, "Aaf" -> Sex.MostlyFemale, "Aafke" -> Sex.Unisex, "Aafkea" -> Sex.Female)
        case x => failure(x.toString) 
      }
    }

    "parse lines with special symbols" in {
      val testString = """M  <A/>dolfs                                       2                                  $"""
      val p = new NameParser
      p.parseAll(p.lines, testString) match {
        case p.Success(r, _) => r mustEqual Map("Ādolfs" -> Sex.Male)
        case x => failure(x.toString)
      }
    }
    
  }
 
}
