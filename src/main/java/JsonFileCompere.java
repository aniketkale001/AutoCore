import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.File;

public class JsonFileCompere
{
    public void jsonComparison(File file1, File file2)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();

            // Read JSON files into JsonNode
            JsonNode json1 = mapper.readTree(new File(String.valueOf(file1)));
            JsonNode json2 = mapper.readTree(new File(String.valueOf(file2)));

            // Compare JSONs in STRICT/LENIENT mode (exact match)
            JSONCompareResult result = JSONCompare.compareJSON(json1.toString(), json2.toString(), JSONCompareMode.LENIENT);

            if (result.passed())
            {
                System.out.println("JSON files are identical!");
            }
            else
            {
                System.out.println("JSON files have differences:");
                System.out.println(result.getMessage());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}