package me.ukstv.sexmachine

import scala.util.parsing.combinator._
import scala.collection.immutable.HashMap

class NameParser extends JavaTokenParsers {

  /**
   * Non-unicode substitutes and their unicode alternatives.
   */
  val REPLACE_TABLE = Map("<A/>" -> "Ā",
                          "<a/>" -> "ā",
                          "<Â>"  -> "Ă",
                          "<â>"  -> "ă",
                          "<A,>" -> "Ą",
                          "<a,>" -> "ą",
                          "<C´>" -> "Ć",
                          "<c´>" -> "ć",
                          "<C^>" -> "Č",
                          "<CH>" -> "Č",
                          "<c^>" -> "č",
                          "<ch>" -> "č",
                          "<d´>" -> "ď",
                          "<Ð>"  -> "Ð",
                          "<DJ"  -> "Ð",
                          "<ð>"  -> "đ",
                          "<dj>" -> "đ",
                          "<E/>" -> "Ē",
                          "<e/>" -> "ē",
                          "<E°>" -> "Ė",
                          "<e°>" -> "ė",
                          "<E,>" -> "Ę",
                          "<e,>" -> "ę",
                          "<Ê>"  -> "Ě",
                          "<ê>"  -> "ě",
                          "<G^>" -> "Ğ",
                          "<g^>" -> "ğ",
                          "<G,>" -> "Ģ",
                          "<g´>" -> "ģ",
                          "<I/>" -> "Ī",
                          "<i/>" -> "ī",
                          "<I°>" -> "İ",
                          "<i>"  -> "ı",
                          "<IJ>" -> "Ĳ",
                          "<ij>" -> "ĳ",
                          "<K,>" -> "Ķ",
                          "<k,>" -> "ķ",
                          "<L,>" -> "Ļ",
                          "<l,>" -> "ļ",
                          "<L´>" -> "Ľ",
                          "<l´>" -> "ľ",
                          "<L/>" -> "Ł",
                          "<l/>" -> "ł",
                          "<N,>" -> "Ņ",
                          "<n,>" -> "ņ",
                          "<N^>" -> "Ň",
                          "<n^>" -> "ň",
                          "<Ö>"  -> "Ő",
                          "<ö>"  -> "ő",
                          " "    -> "Œ",
                          "<OE>" -> "Œ",
                          " "    -> "œ",
                          "<oe>" -> "œ",
                          "<R^>" -> "Ř",
                          "<r^>" -> "ř",
                          "<S,>" -> "Ş",
                          "<s,>" -> "ş",
                          " "    -> "Š",
                          "<S^>" -> "Š",
                          "<SCH>"-> "Š",
                          "<SH>" -> "Š",
                          " "    -> "š",
                          "<s^>" -> "š",
                          "<sch>"-> "š",
                          "<sh>" -> "š",
                          "<T,>" -> "Ţ",
                          "<t,>" -> "ţ",
                          "<t´>" -> "ť",
                          "<U/>" -> "Ū",
                          "<u/>" -> "ū",
                          "<U°>" -> "Ů",
                          "<u°>" -> "ů",
                          "<U,>" -> "Ų",
                          "<u,>" -> "ų",
                          "<Z°>" -> "Ż",
                          "<z°>" -> "ż",
                          "<Z^>" -> "Ž",
                          "<z^>" -> "ž")
                          
  
  override def skipWhitespace = false
  def whitespace = """\s+""".r

  def lines = repsep(comment | nameLine, '\n') ^^ { list =>
    list.foldLeft(HashMap[String, Sex]()) {
      case (memo, None) => memo
      case (memo, Some((name, sex))) =>
        if (name.contains("+")) {
          memo.updated(name.replace("+", " "), sex)
              .updated(name.replace("+", "-"), sex)
              .updated(name.replace("+", ""), sex)
        } else {
          memo.updated(name, sex)
        }
    }
  }
  
  def comment = """#.*\$""".r ^^ {_ => None}

  def nameLine   = sex ~ name ~ umlaut ~ popularityByCountry <~ "$" ^^ {
    case sex ~ name ~ umlaut ~ pop => Some(name -> sex)
  }
  
  def popularityByCountry = """[\s1-9ABCD]{56}""".r
  
  def umlaut = "+" | "-" | " "
  
  def sex = ("M" | "F" | "?F" | "?M" | "?") <~ whitespace ^^ {
    case "M" => Sex.Male
    case "F" => Sex.Female
    case "?F" => Sex.MostlyFemale
    case "?M" => Sex.MostlyMale
    case _ => Sex.Unisex
  }
  
  def name = """[\p{L}\s\<\>\/]{26}""".r ^^ nameCleaner
  
  /**
   * Remove whitespace and replace non-unicode substitutes into
   * unicode chars.
   */
  def nameCleaner(raw: String): String = REPLACE_TABLE.foldLeft(raw.filterNot(_ == ' ')) {
    case (name, (what, rep)) => name.replaceAll(what, rep)
  }

}
