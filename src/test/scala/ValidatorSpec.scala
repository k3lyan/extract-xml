import Controllers.Validator
import cats.data.Validated.Invalid
import cats.data.Validated.Valid
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.wordspec.AnyWordSpecLike

class ValidatorSpec extends AnyWordSpecLike {

  "ArgumentValidator" when {

    "Number of arguments is false will fail" in {
      Validator(List("path_to_XML", "13", "extra argument")).validateNumberOfArguments.isValid should be(false)
    }

    "Path file is valid will load" in {
      val validXML = "./src/test/resources/valid.xml"
      Validator(List(validXML, "13")).validateXMLFile match {
        case Valid(_) => succeed
        case Invalid(errors) => fail(s"This case should not happen")
      }
    }

    "Path file is in invalid will fail" in {
      val invalidXML = "./src/test/resource"
      Validator(List(invalidXML, "13")).validateXMLFile.isValid should be(false)
    }

    "XML file is valid will load" in {
      val validXML = "./src/test/resources/valid.xml"
      Validator(List(validXML, "13")).validateloadSuppliers match {
        case Valid(_) => succeed
        case Invalid(errors) => fail(s"This case should not happen")
      }
    }

    "XML file is in invalid (wrong property type for age) will fail" in {
      val invalidXML = "./src/test/resources/invalid.xml"
         Validator(List(invalidXML, "13")).validateloadSuppliers.isValid should be(false)
    }


    "elapsed time is not an integer will fail" in {
      val validXML = "./src/test/resources/valid.xml"
      Validator(List(validXML, "thirteen")).validateElapsedTime.isValid should be(false)
      }
    }
}
