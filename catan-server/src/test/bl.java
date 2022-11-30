import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.core.card.type.SpecialCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class bl {

    @Test
    public void test(){
        ReflectionUtils.getInstancesOf(SpecialCard.class, "src/main/java/fr/univnantes/alma/commons/card/special")
                .forEach(a -> System.out.println(a.getName()));
        Assertions.assertEquals(1,1);
    }
}
