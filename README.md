Sex Machine
===========

The library helps to define a sex of a person by name. It uses the
underlying data prepared by Jorg Michael for program "gender".

    import me.ukstv.sexmachine.Detector
    
    Detector.getGender("Bob")    // Male
    Detector.getGender("Sally")  // Female
    Detector.getGender("Pauley") // Unisex

The result may be one of Unisex, Male, Female, MostlyMale, or
MostlyFemale. Any unknown name is considered Unisex, unless you use
`Detector.getGenderOpt`:

    Detector.getGenderOpt("Jordan")  // MostlyMale
    Detector.getGenderOpt("Jordano") // None
