package me.ukstv.sexmachine

import scala.io.Source
import scala.io.Codec

object Detector {
  
  val DICT_FILENAME = "/nam_dict.txt"
  
  private lazy val names = readNames()
  
  def getGender(name: String) = names.get(name) match {
    case Some(sex) => sex
    case None => Sex.Unisex
  }
  
  def getGenderOpt(name: String) = names.get(name) 
  
  private def readNames(): Map[String, Sex] = {
    val inputStream = getClass.getResourceAsStream(DICT_FILENAME)
    val lines = Source.createBufferedSource(inputStream)(Codec.ISO8859).getLines
    val p = new NameParser
    lines.foldLeft(Map[String, Sex]()){ case (memo, line) =>
      p.parseAll(p.lines, line) match {
        case p.Success(r, _) => memo ++ r
        case x => memo    
      }
    }
  }
  
}