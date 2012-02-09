package code.snippet
import scala.xml._
import net.liftweb.util.Helpers._
import scala.xml.transform._
import org.scalatest.FunSuite
import org.scalatest.FeatureSpec

import com.damianhelme.TBUtils.snippet.TBUtils;
import com.damianhelme.TBUtils.snippet.TBUtils.*;

import TBUtils._

class TBUtilsTest extends FunSuite {
  
  test("set class where one previously existed") {
    val a = new UnprefixedAttribute("class",Text("old"),Null)
    expect("old foo") {
      TBUtils.appendToClass(a, "foo").get("class").map(_.mkString).get
    }
  }
  test("set class where no class previously existed") {
    val x @ Elem(_,_,a,_,_*)= <li>blah</li>
    expect("foo") {
      TBUtils.appendToClass(a, "foo").get("class").map(_.mkString).get
    }
  }
  test("set class where class previously existed but had blank value") {
    val x @ Elem(_,_,a,_,_*)= <li class="">blah</li>
    expect("foo") {
      val ret = TBUtils.appendToClass(a, "foo").get("class").map(_.mkString).get
      ret
    }   
    
  }
  
  test("set class where other attributes set") {
    val a = new UnprefixedAttribute("class",Text("aclass"),Null)
    val b = new UnprefixedAttribute("href",Text("bhref"),a)
    expect("aclass foo") {
      debug("start: b = " + b)
      val ret = TBUtils.appendToClass(a, "foo").get("class").map(_.mkString).get
      debug("end")
      ret
    }
  }
 
  
  test("three"){
    /* aiming for: 
   val target = <ul>
          <li class="dropdown" data-dropdown="dropdown">
            <a href="#" class="dropdown-toggle">Dropdown</a>
            <ul class="dropdown-menu">
              <li><a href="#">Secondary link</a></li>
              <li><a href="#">Something else here</a></li>
              <li class="divider"></li>
              <li><a href="#">Another link</a></li>
            </ul>
            <li>some other</li>
          </li>
        </ul>
     * */
    val target : NodeSeq = <ul><li class="dropdown" data-dropdown="dropdown"><a href="#" class="dropdown-toggle">Dropdown</a><ul class="dropdown-menu"><li><a href="#">Secondary link</a></li><li><a href="#">Something else here</a></li><li><a href="#">Another link</a></li></ul></li><li>some other</li></ul>
    val in = <ul>
          <li>
            <a href="#" >Dropdown</a>
            <ul>
              <li><a href="#">Secondary link</a></li>
              <li><a href="#">Something else here</a></li>
              <li><a href="#">Another link</a></li>
            </ul>
          </li>
          <li>some other</li>
        </ul>
  
    val target2 = <ul></ul>
    val in2 = <ul></ul>
      
//     val result : NodeSeq = menuToTBNav(in)
    val result2 : NodeSeq = menuToTBNav(in2)
    // println("result is: " + new PrettyPrinter(80,3).formatNodes(result))
    // println("target is: " + new PrettyPrinter(80,3).formatNodes(target))
    // println("result is: " + result)
    // println("target is: " + target)
    
   print("comp: " + (in2 == result2))
   print("in2: " + in2)
   print("result2: " + result2)
    expect(dummyTrans(in2) ) {
      result2
   }
  }
  
  /*
  def appendToClass(attribs: MetaData, newClass: String ) = {
    val curClass = getClassFromAttribs(attribs)
    val resultingClass = if (curClass == "") newClass else curClass + " " + newClass
    attribs.append("class" -> resultingClass)
    
  }
  def getClassFromAttribs( attribs: MetaData ) = {
    attribs.get("class").map(_.mkString).getOrElse("")
  }
  
  def currentClass(in: Node) : String = {
    in.attribute("class").map(_.mkString).getOrElse("")
  }
  def appendClass2( in: Elem, newClass: String ) : Elem = {
    ("* [class+]" #> newClass)(in).head.asInstanceOf[Elem]
  }
  def appendClass( in: Elem, newClass: String ) : Elem = {
    // attribute returns a Option[Seq[scala.xml.Node]]
    // just need to concatinate these nodes
    val curClass = currentClass(in)
    val classToAppend = if ( curClass == "" ) newClass else curClass + " " + newClass
    in % ( "class" -> classToAppend ) 
    // in % ("class" -> "dropdown-toggle")
  }
  */
def dummyTrans( in: NodeSeq ) : NodeSeq = {

    object t1 extends RewriteRule {
      override def transform(n: Node): Seq[Node] = n match {

      // removes the white space which appears between elements 
      case Text(text) if ( text.matches("\\s+") ) => NodeSeq.Empty
     
      case other @ _ => other
      }
    }

    // debug("menuToTBNav received: " + new PrettyPrinter(80,3).formatNodes(in))
    object rt1 extends RuleTransformer(t1)
    val out = rt1.transform(in)
    // debug("menuToTBNav out: " + new PrettyPrinter(80,3).formatNodes(out))
    out
  }
 
}