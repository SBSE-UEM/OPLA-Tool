package br.ufpr.dinf.gres.api.resource.objectivefunctions;

import br.ufpr.dinf.gres.api.base.BaseResource;
import br.ufpr.dinf.gres.domain.entity.objectivefunctions.COEObjectiveFunction;
import br.ufpr.dinf.gres.domain.entity.objectivefunctions.DCObjectiveFunction;
import br.ufpr.dinf.gres.persistence.base.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dc-objective-function")
public class DCObjectiveFunctionResource extends BaseResource<DCObjectiveFunction> {

    public DCObjectiveFunctionResource(BaseService<DCObjectiveFunction> service) {
        super(service);
    }
}