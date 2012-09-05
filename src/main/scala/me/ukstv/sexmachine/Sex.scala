package me.ukstv.sexmachine

sealed abstract class Sex

object Sex {
  case object Male extends Sex
  case object Female extends Sex
  case object MostlyMale extends Sex
  case object MostlyFemale extends Sex
  case object Unisex extends Sex
}