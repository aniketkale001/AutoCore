import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;
import org.xmlunit.builder.Input;

import java.io.File;
import java.util.Iterator;

public class XMLCompare
{
    public void compareXML(File file1, File file2)
    {
        File xml_file1 = new File(String.valueOf(file1));
        File xml_file2 = new File(String.valueOf(file2));
        // Compare XMLs
        Diff diff = DiffBuilder.compare(Input.fromFile(xml_file1))
                .withTest(Input.fromFile(xml_file2))
                .ignoreWhitespace()
                .checkForSimilar() // use checkForIdentical() for stricter comparison
                .build();

        if (!diff.hasDifferences())
        {
            System.out.println("XML files are identical.");
        }
        else
        {
            System.out.println("XML files have differences:");
            Iterator<Difference> differences = diff.getDifferences().iterator();
            while (differences.hasNext())
            {
                System.out.println(differences.next());
            }
        }
    }
}