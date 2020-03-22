package ca.ulaval.glo2003.beds.domain;

import static ca.ulaval.glo2003.beds.domain.helpers.BedObjectMother.*;
import static ca.ulaval.glo2003.beds.rest.mappers.BedMatcherMapper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ca.ulaval.glo2003.beds.domain.assemblers.BedQueryParamAssembler;
import ca.ulaval.glo2003.beds.domain.assemblers.BedTypeQueryParamAssembler;
import ca.ulaval.glo2003.beds.exceptions.*;
import ca.ulaval.glo2003.locations.domain.ZipCode;
import ca.ulaval.glo2003.locations.infrastructure.ZippopotamusClient;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BedQueryFactoryTest {

  private static BedQueryFactory bedQueryFactory;
  private static BedQueryBuilder bedQueryBuilder = mock(BedQueryBuilder.class);
  private static BedQueryBuilder filteredBedQueryBuilder = mock(BedQueryBuilder.class);
  private static BedQueryBuilder otherFilteredBedQueryBuilder = mock(BedQueryBuilder.class);
  private static ZippopotamusClient zippopotamusClient;

  private BedTypeQueryParamAssembler queryAssembler = mock(BedTypeQueryParamAssembler.class);
  private BedTypeQueryParamAssembler otherQueryAssembler = mock(BedTypeQueryParamAssembler.class);
  private ZipCode origin = createZipCode();
  private int maxDistance = 30;
  private BedQuery query = mock(BedQuery.class);
  private BedQuery filteredQuery = mock(BedQuery.class);
  private BedQuery otherFilteredQuery = mock(BedQuery.class);
  private Map<String, String[]> params = new HashMap<>();

  private List<BedQueryParamAssembler> queryParamAssemblers =
      Collections.singletonList(queryAssembler);
  private List<BedQueryParamAssembler> otherQueryParamAssemblers =
      Arrays.asList(queryAssembler, otherQueryAssembler);

  @BeforeEach
  public void setUpMocks() {
    when(bedQueryBuilder.aBedQuery()).thenReturn(bedQueryBuilder);
    when(bedQueryBuilder.build()).thenReturn(query);
    when(filteredBedQueryBuilder.build()).thenReturn(filteredQuery);
    when(otherFilteredBedQueryBuilder.build()).thenReturn(otherFilteredQuery);
    when(queryAssembler.assemble(bedQueryBuilder, params)).thenReturn(filteredBedQueryBuilder);
    when(otherQueryAssembler.assemble(filteredBedQueryBuilder, params))
        .thenReturn(otherFilteredBedQueryBuilder);
  }

  @Test
  public void create_withoutAssembler_shouldCreateQuery() {
    bedQueryFactory =
        new BedQueryFactory(bedQueryBuilder, Collections.emptyList(), zippopotamusClient);

    BedQuery actualQuery = bedQueryFactory.create(params);

    assertSame(query, actualQuery);
  }

  @Test
  public void create_withSingleAssembler_shouldCreateQuery() {
    bedQueryFactory =
        new BedQueryFactory(bedQueryBuilder, queryParamAssemblers, zippopotamusClient);

    BedQuery actualQuery = bedQueryFactory.create(params);

    assertSame(filteredQuery, actualQuery);
  }

  @Test
  public void create_withMultipleAssemblers_shouldCreateQuery() {
    bedQueryFactory =
        new BedQueryFactory(bedQueryBuilder, otherQueryParamAssemblers, zippopotamusClient);

    BedQuery actualQuery = bedQueryFactory.create(params);

    assertSame(otherFilteredQuery, actualQuery);
  }

  /*
  @Test
  public void create_withOrigin_shouldCreateFilteredQuery() {
    params.put(ORIGIN_PARAM, new String[] {origin.getValue()});
    when(bedQueryBuilder.withOrigin(origin)).thenReturn(bedQueryBuilder);

    BedQuery actualQuery = bedQueryFactory.create(params);

    assertSame(query, actualQuery);
  }

  @Test
  public void create_withMaxDistance_shouldCreateFilteredQuery() {
    params.put(MAX_DISTANCE_PARAM, new String[] {Integer.toString(maxDistance)});
    when(bedQueryBuilder.withMaxDistance(maxDistance)).thenReturn(bedQueryBuilder);

    BedQuery actualQuery = bedQueryFactory.create(params);

    assertSame(query, actualQuery);
  }

  @Test
  public void create_withNegativeMaxDistance_shouldThrowInvalidMaxDistanceException() {
    int invalidMaxDistance = -1;
    params.put(MAX_DISTANCE_PARAM, new String[] {Integer.toString(invalidMaxDistance)});

    assertThrows(InvalidMaxDistanceException.class, () -> bedQueryFactory.create(params));
  }

  @Test
  public void create_withInvalidMaxDistance_shouldThrowInvalidMaxDistanceException() {
    params.put(MAX_DISTANCE_PARAM, new String[] {"invalidMaxDistance"});

    assertThrows(InvalidMaxDistanceException.class, () -> bedQueryFactory.create(params));
  }
  */
}
