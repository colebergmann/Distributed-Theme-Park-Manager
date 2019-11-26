package tayto.parkmanager.graphql;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;
import tayto.parkmanager.ManagerDriver;

@Component
public class GraphQLDataFetchers {
    public DataFetcher getAttractionDetails() {
        return dataFetchingEnvironment -> {
            return ManagerDriver.getInstance().getAttractionDetails();
        };
    }

    public DataFetcher getAttractionById() {
        return dataFetchingEnvironment -> {
            String attractionId = dataFetchingEnvironment.getArgument("id");
            return ManagerDriver.getInstance().getAttractionDetails()
                    .stream()
                    .filter(attraction -> attraction.getAttributes().getAttractionId().equals(attractionId))
                    .findFirst()
                    .orElse(null);
        };
    }

}