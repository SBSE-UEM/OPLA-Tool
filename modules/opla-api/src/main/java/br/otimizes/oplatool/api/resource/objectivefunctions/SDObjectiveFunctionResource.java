package br.otimizes.oplatool.api.resource.objectivefunctions;

import br.otimizes.oplatool.api.base.BaseResource;
import br.otimizes.oplatool.domain.entity.objectivefunctions.SDObjectiveFunction;
import br.otimizes.oplatool.persistence.base.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sd-objective-function")
public class SDObjectiveFunctionResource extends BaseResource<SDObjectiveFunction> {

    public SDObjectiveFunctionResource(BaseService<SDObjectiveFunction> service) {
        super(service);
    }
}