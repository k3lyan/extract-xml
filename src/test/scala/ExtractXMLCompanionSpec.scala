import Model.Suppliers
import cats.data.Validated.Invalid
import cats.data.Validated.Valid
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.wordspec.AnyWordSpecLike

import java.io.File

class ExtractXMLCompanionSpec extends AnyWordSpecLike {

  "Suppliers" when {
    "file is valid will load" in {
      val validXML = "./src/test/resources/valid.xml"
      Suppliers.getInfo(validXML) match {
        case Valid(_) =>
          succeed
        case Invalid(errors) =>
          fail(s"Invalid xml could not be loaded to Suppliers\n $errors")
      }
    }

    "file in invalid (wrong property type for age) will fail" in {
      val invalidXML = "./src/test/resources/invalid.xml"
      Suppliers.getInfo(invalidXML).isValid should be(false)
    }
  }
}
