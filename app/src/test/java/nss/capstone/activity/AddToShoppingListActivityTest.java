package nss.capstone.activity;

import nss.capstone.dynamodb.ListDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.openMocks;

class AddToShoppingListActivityTest {

    @Mock
    ListDao listDao;

    AddToShoppingListActivity listActivity;

    @BeforeEach
    void setup() {
        openMocks(this);
        listActivity = new AddToShoppingListActivity(listDao);
    }

    @Test
    public void handleRequest_listExists_addsNewItems() {

    }

    @Test
    public void handleRequest_listDoesNotExist_createsNewList() {


    }
}