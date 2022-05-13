package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import javax.xml.transform.Templates;

public class GT4500Test {

  private TorpedoStore mockTorpedoStorePrimary;
  private TorpedoStore mockTorpedoStoreSecondary;
  private GT4500 ship;

  @BeforeEach
  public void init(){
    mockTorpedoStorePrimary = mock(TorpedoStore.class);
    mockTorpedoStoreSecondary = mock(TorpedoStore.class);
    this.ship = new GT4500(mockTorpedoStorePrimary, mockTorpedoStoreSecondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockTorpedoStorePrimary.isEmpty()).thenReturn(false);
    when(mockTorpedoStorePrimary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockTorpedoStorePrimary, times(1)).isEmpty();
    verify(mockTorpedoStorePrimary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Twice_Success(){
    // Arrange
    when(mockTorpedoStorePrimary.isEmpty()).thenReturn(false);
    when(mockTorpedoStorePrimary.fire(1)).thenReturn(true);
    when(mockTorpedoStoreSecondary.isEmpty()).thenReturn(false);
    when(mockTorpedoStoreSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    verify(mockTorpedoStorePrimary, times(1)).isEmpty();
    verify(mockTorpedoStorePrimary, times(1)).fire(1);
    verify(mockTorpedoStoreSecondary, times(1)).isEmpty();
    verify(mockTorpedoStoreSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Primary_Empty_Success(){
    // Arrange
    when(mockTorpedoStorePrimary.isEmpty()).thenReturn(true);
    when(mockTorpedoStoreSecondary.isEmpty()).thenReturn(false);
    when(mockTorpedoStoreSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockTorpedoStorePrimary, times(1)).isEmpty();
    verify(mockTorpedoStoreSecondary, times(1)).isEmpty();
    verify(mockTorpedoStoreSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Secondary_Empty_Success(){
    // Arrange
    when(mockTorpedoStorePrimary.isEmpty()).thenReturn(false);
    when(mockTorpedoStorePrimary.fire(1)).thenReturn(true);
    when(mockTorpedoStoreSecondary.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    verify(mockTorpedoStorePrimary, times(2)).isEmpty();
    verify(mockTorpedoStorePrimary, times(2)).fire(1);
    verify(mockTorpedoStoreSecondary, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_Single_Both_Empty_Fail(){
    // Arrange
    when(mockTorpedoStorePrimary.isEmpty()).thenReturn(true);
    when(mockTorpedoStoreSecondary.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(mockTorpedoStorePrimary, times(1)).isEmpty();
    verify(mockTorpedoStoreSecondary, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockTorpedoStorePrimary.isEmpty()).thenReturn(false);
    when(mockTorpedoStoreSecondary.isEmpty()).thenReturn(false);
    when(mockTorpedoStorePrimary.fire(1)).thenReturn(true);
    when(mockTorpedoStoreSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockTorpedoStorePrimary, times(1)).isEmpty();
    verify(mockTorpedoStoreSecondary, atMost(1)).isEmpty();
    verify(mockTorpedoStorePrimary, times(1)).fire(1);
    verify(mockTorpedoStoreSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Fail(){
    // Arrange
    when(mockTorpedoStorePrimary.isEmpty()).thenReturn(true);
    when(mockTorpedoStoreSecondary.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(mockTorpedoStorePrimary, times(1)).isEmpty();
    verify(mockTorpedoStoreSecondary, times(1)).isEmpty();
  }

}
