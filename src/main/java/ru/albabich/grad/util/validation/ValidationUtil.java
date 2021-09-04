package ru.albabich.grad.util.validation;

import lombok.experimental.UtilityClass;
import ru.albabich.grad.error.IllegalRequestDataException;
import ru.albabich.grad.error.NotFoundException;
import ru.albabich.grad.HasId;
import ru.albabich.grad.error.VoteException;

import java.time.LocalTime;

@UtilityClass
public class ValidationUtil {
    private static final LocalTime CLOSEREVOTING = LocalTime.of(11, 0);


    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void checkModification(int count, int id) {
        if (count == 0) {
            throw new NotFoundException("Entity with id=" + id + " not found");
        }
    }

    public static void checkChangeVoteAbility() {
        if (LocalTime.now().isAfter(CLOSEREVOTING)) {
            throw new VoteException(String.format("You can't change vote after %s", CLOSEREVOTING));
        }
    }
}