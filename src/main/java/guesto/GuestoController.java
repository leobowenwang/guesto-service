package guesto;

import io.micronaut.http.annotation.*;

@Controller("/guesto")
public class GuestoController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}