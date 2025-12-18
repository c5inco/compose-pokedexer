#import <Foundation/NSArray.h>
#import <Foundation/NSDictionary.h>
#import <Foundation/NSError.h>
#import <Foundation/NSObject.h>
#import <Foundation/NSSet.h>
#import <Foundation/NSString.h>
#import <Foundation/NSValue.h>

@class NSData, NSError, SharedAbilitiesDao_ImplCompanion, SharedAbilitiesQueryAbility, SharedAbilitiesQueryCompanion, SharedAbilitiesQueryData, SharedAbilitiesQueryFlavorText, SharedAbilitiesQuerySelections, SharedAbilitiesQuery_ResponseAdapter, SharedAbilitiesQuery_ResponseAdapterAbility, SharedAbilitiesQuery_ResponseAdapterData, SharedAbilitiesQuery_ResponseAdapterFlavorText, SharedAbility, SharedApolloClientProvider, SharedApollo_apiAdapterContext, SharedApollo_apiAdapterContextBuilder, SharedApollo_apiApolloRequest<D>, SharedApollo_apiApolloRequestBuilder<D>, SharedApollo_apiApolloResponse<D>, SharedApollo_apiApolloResponseBuilder<D>, SharedApollo_apiCompiledArgument, SharedApollo_apiCompiledCondition, SharedApollo_apiCompiledField, SharedApollo_apiCompiledFieldBuilder, SharedApollo_apiCompiledNamedType, SharedApollo_apiCompiledSelection, SharedApollo_apiCompiledType, SharedApollo_apiCustomScalarAdapters, SharedApollo_apiCustomScalarAdaptersBuilder, SharedApollo_apiCustomScalarAdaptersKey, SharedApollo_apiCustomScalarType, SharedApollo_apiCustomTypeValue<T>, SharedApollo_apiCustomTypeValueCompanion, SharedApollo_apiDeferredFragmentIdentifier, SharedApollo_apiError, SharedApollo_apiErrorLocation, SharedApollo_apiExecutableVariables, SharedApollo_apiHttpHeader, SharedApollo_apiHttpMethod, SharedApollo_apiHttpRequest, SharedApollo_apiHttpRequestBuilder, SharedApollo_apiHttpResponse, SharedApollo_apiHttpResponseBuilder, SharedApollo_apiInterfaceType, SharedApollo_apiInterfaceTypeBuilder, SharedApollo_apiJsonNumber, SharedApollo_apiJsonReaderToken, SharedApollo_apiObjectType, SharedApollo_apiObjectTypeBuilder, SharedApollo_runtimeApolloCall<D>, SharedApollo_runtimeApolloClient, SharedApollo_runtimeApolloClientBuilder, SharedApollo_runtimeApolloClientCompanion, SharedApollo_runtimeWsFrameType, SharedApollo_runtimeWsProtocol, SharedEvolution, SharedEvolutionTrigger, SharedEvolutionTriggerCompanion, SharedGeneration, SharedGenerationCompanion, SharedGenerationInfo, SharedGraphQLBooleanCompanion, SharedGraphQLFloatCompanion, SharedGraphQLIDCompanion, SharedGraphQLIntCompanion, SharedGraphQLStringCompanion, SharedItem, SharedItemsDao_ImplCompanion, SharedItemsQueryCompanion, SharedItemsQueryData, SharedItemsQueryFlavorText, SharedItemsQueryInfo, SharedItemsQueryItem, SharedItemsQuerySelections, SharedItemsQueryTotal, SharedItemsQuery_ResponseAdapter, SharedItemsQuery_ResponseAdapterData, SharedItemsQuery_ResponseAdapterFlavorText, SharedItemsQuery_ResponseAdapterInfo, SharedItemsQuery_ResponseAdapterItem, SharedItemsQuery_ResponseAdapterTotal, SharedKotlinAbstractCoroutineContextElement, SharedKotlinAbstractCoroutineContextKey<B, E>, SharedKotlinArray<T>, SharedKotlinByteArray, SharedKotlinByteIterator, SharedKotlinEnum<E>, SharedKotlinEnumCompanion, SharedKotlinException, SharedKotlinIllegalStateException, SharedKotlinIntArray, SharedKotlinIntIterator, SharedKotlinNothing, SharedKotlinRuntimeException, SharedKotlinThrowable, SharedKotlinUnit, SharedKotlinx_coroutines_coreCoroutineDispatcher, SharedKotlinx_coroutines_coreCoroutineDispatcherKey, SharedMove, SharedMoveCategory, SharedMovesDao_ImplCompanion, SharedOkioBuffer, SharedOkioBufferUnsafeCursor, SharedOkioByteString, SharedOkioByteStringCompanion, SharedOkioTimeout, SharedOkioTimeoutCompanion, SharedPokedexerDatabase, SharedPokedexerDatabaseConstructor, SharedPokedexerSDK, SharedPokemon, SharedPokemonAbility, SharedPokemonDao_ImplCompanion, SharedPokemonMove, SharedPokemonOriginalMovesQueryCategory, SharedPokemonOriginalMovesQueryCompanion, SharedPokemonOriginalMovesQueryData, SharedPokemonOriginalMovesQueryDescription, SharedPokemonOriginalMovesQueryInfo, SharedPokemonOriginalMovesQueryMove, SharedPokemonOriginalMovesQuerySelections, SharedPokemonOriginalMovesQueryTotal, SharedPokemonOriginalMovesQueryType, SharedPokemonOriginalMovesQuery_ResponseAdapter, SharedPokemonOriginalMovesQuery_ResponseAdapterCategory, SharedPokemonOriginalMovesQuery_ResponseAdapterData, SharedPokemonOriginalMovesQuery_ResponseAdapterDescription, SharedPokemonOriginalMovesQuery_ResponseAdapterInfo, SharedPokemonOriginalMovesQuery_ResponseAdapterMove, SharedPokemonOriginalMovesQuery_ResponseAdapterTotal, SharedPokemonOriginalMovesQuery_ResponseAdapterType, SharedPokemonOriginalQuery, SharedPokemonOriginalQueryAbility, SharedPokemonOriginalQueryCompanion, SharedPokemonOriginalQueryData, SharedPokemonOriginalQueryDescription, SharedPokemonOriginalQueryDetail, SharedPokemonOriginalQueryEvolution, SharedPokemonOriginalQueryEvolutionChain, SharedPokemonOriginalQueryInfo, SharedPokemonOriginalQueryMove, SharedPokemonOriginalQueryPokemon, SharedPokemonOriginalQuerySelections, SharedPokemonOriginalQuerySpecies, SharedPokemonOriginalQueryStat, SharedPokemonOriginalQueryStat1, SharedPokemonOriginalQueryTargetLevel, SharedPokemonOriginalQueryTotal, SharedPokemonOriginalQueryType, SharedPokemonOriginalQueryType1, SharedPokemonOriginalQuery_ResponseAdapter, SharedPokemonOriginalQuery_ResponseAdapterAbility, SharedPokemonOriginalQuery_ResponseAdapterData, SharedPokemonOriginalQuery_ResponseAdapterDescription, SharedPokemonOriginalQuery_ResponseAdapterDetail, SharedPokemonOriginalQuery_ResponseAdapterEvolution, SharedPokemonOriginalQuery_ResponseAdapterEvolutionChain, SharedPokemonOriginalQuery_ResponseAdapterInfo, SharedPokemonOriginalQuery_ResponseAdapterMove, SharedPokemonOriginalQuery_ResponseAdapterPokemon, SharedPokemonOriginalQuery_ResponseAdapterSpecies, SharedPokemonOriginalQuery_ResponseAdapterStat, SharedPokemonOriginalQuery_ResponseAdapterStat1, SharedPokemonOriginalQuery_ResponseAdapterTargetLevel, SharedPokemonOriginalQuery_ResponseAdapterTotal, SharedPokemonOriginalQuery_ResponseAdapterType, SharedPokemonOriginalQuery_ResponseAdapterType1, SharedPokemonOriginalQuery_VariablesAdapter, SharedPokemon_v2_abilityCompanion, SharedPokemon_v2_abilityflavortextCompanion, SharedPokemon_v2_evolutionchainCompanion, SharedPokemon_v2_itemCompanion, SharedPokemon_v2_item_aggregateCompanion, SharedPokemon_v2_item_aggregate_fieldsCompanion, SharedPokemon_v2_itemflavortextCompanion, SharedPokemon_v2_moveCompanion, SharedPokemon_v2_move_aggregateCompanion, SharedPokemon_v2_move_aggregate_fieldsCompanion, SharedPokemon_v2_movedamageclassCompanion, SharedPokemon_v2_moveflavortextCompanion, SharedPokemon_v2_pokemonCompanion, SharedPokemon_v2_pokemonabilityCompanion, SharedPokemon_v2_pokemonevolutionCompanion, SharedPokemon_v2_pokemonmoveCompanion, SharedPokemon_v2_pokemonspeciesCompanion, SharedPokemon_v2_pokemonspecies_aggregateCompanion, SharedPokemon_v2_pokemonspecies_aggregate_fieldsCompanion, SharedPokemon_v2_pokemonspeciesflavortextCompanion, SharedPokemon_v2_pokemonspeciesnameCompanion, SharedPokemon_v2_pokemonstatCompanion, SharedPokemon_v2_pokemontypeCompanion, SharedPokemon_v2_statCompanion, SharedPokemon_v2_typeCompanion, SharedQuery_rootCompanion, SharedResult<__covariant R>, SharedResultError, SharedResultSuccess<__covariant T>, SharedRoom_runtimeInvalidationTracker, SharedRoom_runtimeMigration, SharedRoom_runtimeRoomDatabase, SharedRoom_runtimeRoomDatabaseBuilder<T>, SharedRoom_runtimeRoomDatabaseCallback, SharedRoom_runtimeRoomDatabaseJournalMode, SharedRoom_runtimeRoomOpenDelegate, SharedRoom_runtimeRoomOpenDelegateValidationResult, SharedType, SharedUuidUuid;

@protocol SharedAbilitiesDao, SharedApollo_apiAdapter, SharedApollo_apiCustomTypeAdapter, SharedApollo_apiExecutable, SharedApollo_apiExecutableData, SharedApollo_apiExecutionContext, SharedApollo_apiExecutionContextElement, SharedApollo_apiExecutionContextKey, SharedApollo_apiExecutionOptions, SharedApollo_apiHttpBody, SharedApollo_apiJsonReader, SharedApollo_apiJsonWriter, SharedApollo_apiMutableExecutionOptions, SharedApollo_apiMutation, SharedApollo_apiMutationData, SharedApollo_apiOperation, SharedApollo_apiOperationData, SharedApollo_apiQuery, SharedApollo_apiQueryData, SharedApollo_apiSubscription, SharedApollo_apiSubscriptionData, SharedApollo_apiUpload, SharedApollo_runtimeApolloInterceptor, SharedApollo_runtimeApolloInterceptorChain, SharedApollo_runtimeHttpEngine, SharedApollo_runtimeHttpInterceptor, SharedApollo_runtimeHttpInterceptorChain, SharedApollo_runtimeNetworkTransport, SharedApollo_runtimeWebSocketConnection, SharedApollo_runtimeWebSocketEngine, SharedApollo_runtimeWsProtocolFactory, SharedApollo_runtimeWsProtocolListener, SharedFavoritesStore, SharedItemsDao, SharedKotlinAutoCloseable, SharedKotlinComparable, SharedKotlinContinuation, SharedKotlinContinuationInterceptor, SharedKotlinCoroutineContext, SharedKotlinCoroutineContextElement, SharedKotlinCoroutineContextKey, SharedKotlinFunction, SharedKotlinIterator, SharedKotlinKAnnotatedElement, SharedKotlinKClass, SharedKotlinKClassifier, SharedKotlinKDeclarationContainer, SharedKotlinSuspendFunction0, SharedKotlinSuspendFunction2, SharedKotlinx_coroutines_coreCoroutineScope, SharedKotlinx_coroutines_coreFlow, SharedKotlinx_coroutines_coreFlowCollector, SharedKotlinx_coroutines_coreRunnable, SharedKotlinx_coroutines_coreSharedFlow, SharedKotlinx_coroutines_coreStateFlow, SharedMovesDao, SharedOkioBufferedSink, SharedOkioBufferedSource, SharedOkioCloseable, SharedOkioSink, SharedOkioSource, SharedPokemonDao, SharedRoom_runtimeAutoMigrationSpec, SharedRoom_runtimeRoomDatabaseConstructor, SharedRoom_runtimeRoomOpenDelegateMarker, SharedSqliteSQLiteConnection, SharedSqliteSQLiteDriver, SharedSqliteSQLiteStatement;

NS_ASSUME_NONNULL_BEGIN
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wunknown-warning-option"
#pragma clang diagnostic ignored "-Wincompatible-property-type"
#pragma clang diagnostic ignored "-Wnullability"

#pragma push_macro("_Nullable_result")
#if !__has_feature(nullability_nullable_result)
#undef _Nullable_result
#define _Nullable_result _Nullable
#endif

__attribute__((swift_name("KotlinBase")))
@interface SharedBase : NSObject
- (instancetype)init __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
+ (void)initialize __attribute__((objc_requires_super));
@end

@interface SharedBase (SharedBaseCopying) <NSCopying>
@end

__attribute__((swift_name("KotlinMutableSet")))
@interface SharedMutableSet<ObjectType> : NSMutableSet<ObjectType>
@end

__attribute__((swift_name("KotlinMutableDictionary")))
@interface SharedMutableDictionary<KeyType, ObjectType> : NSMutableDictionary<KeyType, ObjectType>
@end

@interface NSError (NSErrorSharedKotlinException)
@property (readonly) id _Nullable kotlinException;
@end

__attribute__((swift_name("KotlinNumber")))
@interface SharedNumber : NSNumber
- (instancetype)initWithChar:(char)value __attribute__((unavailable));
- (instancetype)initWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
- (instancetype)initWithShort:(short)value __attribute__((unavailable));
- (instancetype)initWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
- (instancetype)initWithInt:(int)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
- (instancetype)initWithLong:(long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
- (instancetype)initWithLongLong:(long long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
- (instancetype)initWithFloat:(float)value __attribute__((unavailable));
- (instancetype)initWithDouble:(double)value __attribute__((unavailable));
- (instancetype)initWithBool:(BOOL)value __attribute__((unavailable));
- (instancetype)initWithInteger:(NSInteger)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
+ (instancetype)numberWithChar:(char)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
+ (instancetype)numberWithShort:(short)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
+ (instancetype)numberWithInt:(int)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
+ (instancetype)numberWithLong:(long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
+ (instancetype)numberWithLongLong:(long long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
+ (instancetype)numberWithFloat:(float)value __attribute__((unavailable));
+ (instancetype)numberWithDouble:(double)value __attribute__((unavailable));
+ (instancetype)numberWithBool:(BOOL)value __attribute__((unavailable));
+ (instancetype)numberWithInteger:(NSInteger)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
@end

__attribute__((swift_name("KotlinByte")))
@interface SharedByte : SharedNumber
- (instancetype)initWithChar:(char)value;
+ (instancetype)numberWithChar:(char)value;
@end

__attribute__((swift_name("KotlinUByte")))
@interface SharedUByte : SharedNumber
- (instancetype)initWithUnsignedChar:(unsigned char)value;
+ (instancetype)numberWithUnsignedChar:(unsigned char)value;
@end

__attribute__((swift_name("KotlinShort")))
@interface SharedShort : SharedNumber
- (instancetype)initWithShort:(short)value;
+ (instancetype)numberWithShort:(short)value;
@end

__attribute__((swift_name("KotlinUShort")))
@interface SharedUShort : SharedNumber
- (instancetype)initWithUnsignedShort:(unsigned short)value;
+ (instancetype)numberWithUnsignedShort:(unsigned short)value;
@end

__attribute__((swift_name("KotlinInt")))
@interface SharedInt : SharedNumber
- (instancetype)initWithInt:(int)value;
+ (instancetype)numberWithInt:(int)value;
@end

__attribute__((swift_name("KotlinUInt")))
@interface SharedUInt : SharedNumber
- (instancetype)initWithUnsignedInt:(unsigned int)value;
+ (instancetype)numberWithUnsignedInt:(unsigned int)value;
@end

__attribute__((swift_name("KotlinLong")))
@interface SharedLong : SharedNumber
- (instancetype)initWithLongLong:(long long)value;
+ (instancetype)numberWithLongLong:(long long)value;
@end

__attribute__((swift_name("KotlinULong")))
@interface SharedULong : SharedNumber
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value;
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value;
@end

__attribute__((swift_name("KotlinFloat")))
@interface SharedFloat : SharedNumber
- (instancetype)initWithFloat:(float)value;
+ (instancetype)numberWithFloat:(float)value;
@end

__attribute__((swift_name("KotlinDouble")))
@interface SharedDouble : SharedNumber
- (instancetype)initWithDouble:(double)value;
+ (instancetype)numberWithDouble:(double)value;
@end

__attribute__((swift_name("KotlinBoolean")))
@interface SharedBoolean : SharedNumber
- (instancetype)initWithBool:(BOOL)value;
+ (instancetype)numberWithBool:(BOOL)value;
@end

__attribute__((swift_name("Apollo_apiExecutable")))
@protocol SharedApollo_apiExecutable
@required
- (id<SharedApollo_apiAdapter>)adapter __attribute__((swift_name("adapter()")));
- (SharedApollo_apiCompiledField *)rootField __attribute__((swift_name("rootField()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)serializeVariablesWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("serializeVariables(writer:customScalarAdapters:)")));
@end

__attribute__((swift_name("Apollo_apiOperation")))
@protocol SharedApollo_apiOperation <SharedApollo_apiExecutable>
@required
- (NSString *)document __attribute__((swift_name("document()")));
- (NSString *)id __attribute__((swift_name("id()")));
- (NSString *)name __attribute__((swift_name("name()")));
@end

__attribute__((swift_name("Apollo_apiQuery")))
@protocol SharedApollo_apiQuery <SharedApollo_apiOperation>
@required
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("AbilitiesQuery")))
@interface SharedAbilitiesQuery : SharedBase <SharedApollo_apiQuery>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedAbilitiesQueryCompanion *companion __attribute__((swift_name("companion")));
- (id<SharedApollo_apiAdapter>)adapter __attribute__((swift_name("adapter()")));
- (NSString *)document __attribute__((swift_name("document()")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)id __attribute__((swift_name("id()")));
- (NSString *)name __attribute__((swift_name("name()")));
- (SharedApollo_apiCompiledField *)rootField __attribute__((swift_name("rootField()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)serializeVariablesWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("serializeVariables(writer:customScalarAdapters:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("AbilitiesQuery.Ability")))
@interface SharedAbilitiesQueryAbility : SharedBase
- (instancetype)initWithId:(int32_t)id name:(NSString *)name flavorText:(NSArray<SharedAbilitiesQueryFlavorText *> *)flavorText __attribute__((swift_name("init(id:name:flavorText:)"))) __attribute__((objc_designated_initializer));
- (SharedAbilitiesQueryAbility *)doCopyId:(int32_t)id name:(NSString *)name flavorText:(NSArray<SharedAbilitiesQueryFlavorText *> *)flavorText __attribute__((swift_name("doCopy(id:name:flavorText:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSArray<SharedAbilitiesQueryFlavorText *> *flavorText __attribute__((swift_name("flavorText")));
@property (readonly) int32_t id __attribute__((swift_name("id")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("AbilitiesQuery.Companion")))
@interface SharedAbilitiesQueryCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedAbilitiesQueryCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *OPERATION_DOCUMENT __attribute__((swift_name("OPERATION_DOCUMENT")));
@property (readonly) NSString *OPERATION_ID __attribute__((swift_name("OPERATION_ID")));
@property (readonly) NSString *OPERATION_NAME __attribute__((swift_name("OPERATION_NAME")));
@end

__attribute__((swift_name("Apollo_apiExecutableData")))
@protocol SharedApollo_apiExecutableData
@required
@end

__attribute__((swift_name("Apollo_apiOperationData")))
@protocol SharedApollo_apiOperationData <SharedApollo_apiExecutableData>
@required
@end

__attribute__((swift_name("Apollo_apiQueryData")))
@protocol SharedApollo_apiQueryData <SharedApollo_apiOperationData>
@required
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("AbilitiesQuery.Data")))
@interface SharedAbilitiesQueryData : SharedBase <SharedApollo_apiQueryData>
- (instancetype)initWithAbilities:(NSArray<SharedAbilitiesQueryAbility *> *)abilities __attribute__((swift_name("init(abilities:)"))) __attribute__((objc_designated_initializer));
- (SharedAbilitiesQueryData *)doCopyAbilities:(NSArray<SharedAbilitiesQueryAbility *> *)abilities __attribute__((swift_name("doCopy(abilities:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSArray<SharedAbilitiesQueryAbility *> *abilities __attribute__((swift_name("abilities")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("AbilitiesQuery.FlavorText")))
@interface SharedAbilitiesQueryFlavorText : SharedBase
- (instancetype)initWithDescription:(NSString *)description __attribute__((swift_name("init(description:)"))) __attribute__((objc_designated_initializer));
- (SharedAbilitiesQueryFlavorText *)doCopyDescription:(NSString *)description __attribute__((swift_name("doCopy(description:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *description_ __attribute__((swift_name("description_")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("GenerationInfo")))
@interface SharedGenerationInfo : SharedBase
- (instancetype)initWithId:(int32_t)id name:(NSString *)name romanNumeral:(NSString *)romanNumeral __attribute__((swift_name("init(id:name:romanNumeral:)"))) __attribute__((objc_designated_initializer));
- (SharedGenerationInfo *)doCopyId:(int32_t)id name:(NSString *)name romanNumeral:(NSString *)romanNumeral __attribute__((swift_name("doCopy(id:name:romanNumeral:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t id __attribute__((swift_name("id")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) NSString *romanNumeral __attribute__((swift_name("romanNumeral")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Greeting")))
@interface SharedGreeting : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (NSString *)greet __attribute__((swift_name("greet()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ItemsQuery")))
@interface SharedItemsQuery : SharedBase <SharedApollo_apiQuery>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedItemsQueryCompanion *companion __attribute__((swift_name("companion")));
- (id<SharedApollo_apiAdapter>)adapter __attribute__((swift_name("adapter()")));
- (NSString *)document __attribute__((swift_name("document()")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)id __attribute__((swift_name("id()")));
- (NSString *)name __attribute__((swift_name("name()")));
- (SharedApollo_apiCompiledField *)rootField __attribute__((swift_name("rootField()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)serializeVariablesWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("serializeVariables(writer:customScalarAdapters:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ItemsQuery.Companion")))
@interface SharedItemsQueryCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedItemsQueryCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *OPERATION_DOCUMENT __attribute__((swift_name("OPERATION_DOCUMENT")));
@property (readonly) NSString *OPERATION_ID __attribute__((swift_name("OPERATION_ID")));
@property (readonly) NSString *OPERATION_NAME __attribute__((swift_name("OPERATION_NAME")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ItemsQuery.Data")))
@interface SharedItemsQueryData : SharedBase <SharedApollo_apiQueryData>
- (instancetype)initWithInfo:(SharedItemsQueryInfo *)info __attribute__((swift_name("init(info:)"))) __attribute__((objc_designated_initializer));
- (SharedItemsQueryData *)doCopyInfo:(SharedItemsQueryInfo *)info __attribute__((swift_name("doCopy(info:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) SharedItemsQueryInfo *info __attribute__((swift_name("info")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ItemsQuery.FlavorText")))
@interface SharedItemsQueryFlavorText : SharedBase
- (instancetype)initWithText:(NSString *)text __attribute__((swift_name("init(text:)"))) __attribute__((objc_designated_initializer));
- (SharedItemsQueryFlavorText *)doCopyText:(NSString *)text __attribute__((swift_name("doCopy(text:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *text __attribute__((swift_name("text")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ItemsQuery.Info")))
@interface SharedItemsQueryInfo : SharedBase
- (instancetype)initWithTotal:(SharedItemsQueryTotal * _Nullable)total items:(NSArray<SharedItemsQueryItem *> *)items __attribute__((swift_name("init(total:items:)"))) __attribute__((objc_designated_initializer));
- (SharedItemsQueryInfo *)doCopyTotal:(SharedItemsQueryTotal * _Nullable)total items:(NSArray<SharedItemsQueryItem *> *)items __attribute__((swift_name("doCopy(total:items:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSArray<SharedItemsQueryItem *> *items __attribute__((swift_name("items")));
@property (readonly) SharedItemsQueryTotal * _Nullable total __attribute__((swift_name("total")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ItemsQuery.Item")))
@interface SharedItemsQueryItem : SharedBase
- (instancetype)initWithId:(int32_t)id name:(NSString *)name flavorText:(NSArray<SharedItemsQueryFlavorText *> *)flavorText __attribute__((swift_name("init(id:name:flavorText:)"))) __attribute__((objc_designated_initializer));
- (SharedItemsQueryItem *)doCopyId:(int32_t)id name:(NSString *)name flavorText:(NSArray<SharedItemsQueryFlavorText *> *)flavorText __attribute__((swift_name("doCopy(id:name:flavorText:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSArray<SharedItemsQueryFlavorText *> *flavorText __attribute__((swift_name("flavorText")));
@property (readonly) int32_t id __attribute__((swift_name("id")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ItemsQuery.Total")))
@interface SharedItemsQueryTotal : SharedBase
- (instancetype)initWithCount:(int32_t)count __attribute__((swift_name("init(count:)"))) __attribute__((objc_designated_initializer));
- (SharedItemsQueryTotal *)doCopyCount:(int32_t)count __attribute__((swift_name("doCopy(count:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t count __attribute__((swift_name("count")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokedexerSDK")))
@interface SharedPokedexerSDK : SharedBase
- (instancetype)initWithFavoritesStore:(id<SharedFavoritesStore>)favoritesStore __attribute__((swift_name("init(favoritesStore:)"))) __attribute__((objc_designated_initializer));
- (NSArray<SharedGenerationInfo *> *)getGenerations __attribute__((swift_name("getGenerations()")));
@property (readonly) id<SharedKotlinx_coroutines_coreStateFlow> isLoadingPokemon __attribute__((swift_name("isLoadingPokemon")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuery")))
@interface SharedPokemonOriginalMovesQuery : SharedBase <SharedApollo_apiQuery>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemonOriginalMovesQueryCompanion *companion __attribute__((swift_name("companion")));
- (id<SharedApollo_apiAdapter>)adapter __attribute__((swift_name("adapter()")));
- (NSString *)document __attribute__((swift_name("document()")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)id __attribute__((swift_name("id()")));
- (NSString *)name __attribute__((swift_name("name()")));
- (SharedApollo_apiCompiledField *)rootField __attribute__((swift_name("rootField()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)serializeVariablesWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("serializeVariables(writer:customScalarAdapters:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuery.Category")))
@interface SharedPokemonOriginalMovesQueryCategory : SharedBase
- (instancetype)initWithName:(NSString *)name __attribute__((swift_name("init(name:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalMovesQueryCategory *)doCopyName:(NSString *)name __attribute__((swift_name("doCopy(name:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuery.Companion")))
@interface SharedPokemonOriginalMovesQueryCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalMovesQueryCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *OPERATION_DOCUMENT __attribute__((swift_name("OPERATION_DOCUMENT")));
@property (readonly) NSString *OPERATION_ID __attribute__((swift_name("OPERATION_ID")));
@property (readonly) NSString *OPERATION_NAME __attribute__((swift_name("OPERATION_NAME")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuery.Data")))
@interface SharedPokemonOriginalMovesQueryData : SharedBase <SharedApollo_apiQueryData>
- (instancetype)initWithInfo:(SharedPokemonOriginalMovesQueryInfo *)info moves:(NSArray<SharedPokemonOriginalMovesQueryMove *> *)moves __attribute__((swift_name("init(info:moves:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalMovesQueryData *)doCopyInfo:(SharedPokemonOriginalMovesQueryInfo *)info moves:(NSArray<SharedPokemonOriginalMovesQueryMove *> *)moves __attribute__((swift_name("doCopy(info:moves:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) SharedPokemonOriginalMovesQueryInfo *info __attribute__((swift_name("info")));
@property (readonly) NSArray<SharedPokemonOriginalMovesQueryMove *> *moves __attribute__((swift_name("moves")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuery.Description")))
@interface SharedPokemonOriginalMovesQueryDescription : SharedBase
- (instancetype)initWithFlavorText:(NSString *)flavorText __attribute__((swift_name("init(flavorText:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalMovesQueryDescription *)doCopyFlavorText:(NSString *)flavorText __attribute__((swift_name("doCopy(flavorText:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *flavorText __attribute__((swift_name("flavorText")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuery.Info")))
@interface SharedPokemonOriginalMovesQueryInfo : SharedBase
- (instancetype)initWithTotal:(SharedPokemonOriginalMovesQueryTotal * _Nullable)total __attribute__((swift_name("init(total:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalMovesQueryInfo *)doCopyTotal:(SharedPokemonOriginalMovesQueryTotal * _Nullable)total __attribute__((swift_name("doCopy(total:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) SharedPokemonOriginalMovesQueryTotal * _Nullable total __attribute__((swift_name("total")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuery.Move")))
@interface SharedPokemonOriginalMovesQueryMove : SharedBase
- (instancetype)initWithId:(int32_t)id name:(NSString *)name accuracy:(SharedInt * _Nullable)accuracy category:(SharedPokemonOriginalMovesQueryCategory * _Nullable)category description:(NSArray<SharedPokemonOriginalMovesQueryDescription *> *)description pp:(SharedInt * _Nullable)pp power:(SharedInt * _Nullable)power type:(SharedPokemonOriginalMovesQueryType * _Nullable)type __attribute__((swift_name("init(id:name:accuracy:category:description:pp:power:type:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalMovesQueryMove *)doCopyId:(int32_t)id name:(NSString *)name accuracy:(SharedInt * _Nullable)accuracy category:(SharedPokemonOriginalMovesQueryCategory * _Nullable)category description:(NSArray<SharedPokemonOriginalMovesQueryDescription *> *)description pp:(SharedInt * _Nullable)pp power:(SharedInt * _Nullable)power type:(SharedPokemonOriginalMovesQueryType * _Nullable)type __attribute__((swift_name("doCopy(id:name:accuracy:category:description:pp:power:type:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) SharedInt * _Nullable accuracy __attribute__((swift_name("accuracy")));
@property (readonly) SharedPokemonOriginalMovesQueryCategory * _Nullable category __attribute__((swift_name("category")));
@property (readonly) NSArray<SharedPokemonOriginalMovesQueryDescription *> *description_ __attribute__((swift_name("description_")));
@property (readonly) int32_t id __attribute__((swift_name("id")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) SharedInt * _Nullable power __attribute__((swift_name("power")));
@property (readonly) SharedInt * _Nullable pp __attribute__((swift_name("pp")));
@property (readonly) SharedPokemonOriginalMovesQueryType * _Nullable type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuery.Total")))
@interface SharedPokemonOriginalMovesQueryTotal : SharedBase
- (instancetype)initWithCount:(int32_t)count __attribute__((swift_name("init(count:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalMovesQueryTotal *)doCopyCount:(int32_t)count __attribute__((swift_name("doCopy(count:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t count __attribute__((swift_name("count")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuery.Type_")))
@interface SharedPokemonOriginalMovesQueryType : SharedBase
- (instancetype)initWithName:(NSString *)name __attribute__((swift_name("init(name:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalMovesQueryType *)doCopyName:(NSString *)name __attribute__((swift_name("doCopy(name:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery")))
@interface SharedPokemonOriginalQuery : SharedBase <SharedApollo_apiQuery>
- (instancetype)initWithGenerationId:(int32_t)generationId __attribute__((swift_name("init(generationId:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) SharedPokemonOriginalQueryCompanion *companion __attribute__((swift_name("companion")));
- (id<SharedApollo_apiAdapter>)adapter __attribute__((swift_name("adapter()")));
- (SharedPokemonOriginalQuery *)doCopyGenerationId:(int32_t)generationId __attribute__((swift_name("doCopy(generationId:)")));
- (NSString *)document __attribute__((swift_name("document()")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)id __attribute__((swift_name("id()")));
- (NSString *)name __attribute__((swift_name("name()")));
- (SharedApollo_apiCompiledField *)rootField __attribute__((swift_name("rootField()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)serializeVariablesWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("serializeVariables(writer:customScalarAdapters:)")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t generationId __attribute__((swift_name("generationId")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery.Ability")))
@interface SharedPokemonOriginalQueryAbility : SharedBase
- (instancetype)initWithId:(SharedInt * _Nullable)id isHidden:(BOOL)isHidden __attribute__((swift_name("init(id:isHidden:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalQueryAbility *)doCopyId:(SharedInt * _Nullable)id isHidden:(BOOL)isHidden __attribute__((swift_name("doCopy(id:isHidden:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) SharedInt * _Nullable id __attribute__((swift_name("id")));
@property (readonly) BOOL isHidden __attribute__((swift_name("isHidden")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery.Companion")))
@interface SharedPokemonOriginalQueryCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQueryCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *OPERATION_DOCUMENT __attribute__((swift_name("OPERATION_DOCUMENT")));
@property (readonly) NSString *OPERATION_ID __attribute__((swift_name("OPERATION_ID")));
@property (readonly) NSString *OPERATION_NAME __attribute__((swift_name("OPERATION_NAME")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery.Data")))
@interface SharedPokemonOriginalQueryData : SharedBase <SharedApollo_apiQueryData>
- (instancetype)initWithInfo:(SharedPokemonOriginalQueryInfo *)info pokemon:(NSArray<SharedPokemonOriginalQueryPokemon *> *)pokemon __attribute__((swift_name("init(info:pokemon:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalQueryData *)doCopyInfo:(SharedPokemonOriginalQueryInfo *)info pokemon:(NSArray<SharedPokemonOriginalQueryPokemon *> *)pokemon __attribute__((swift_name("doCopy(info:pokemon:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) SharedPokemonOriginalQueryInfo *info __attribute__((swift_name("info")));
@property (readonly) NSArray<SharedPokemonOriginalQueryPokemon *> *pokemon __attribute__((swift_name("pokemon")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery.Description")))
@interface SharedPokemonOriginalQueryDescription : SharedBase
- (instancetype)initWithFlavorText:(NSString *)flavorText __attribute__((swift_name("init(flavorText:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalQueryDescription *)doCopyFlavorText:(NSString *)flavorText __attribute__((swift_name("doCopy(flavorText:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *flavorText __attribute__((swift_name("flavorText")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery.Detail")))
@interface SharedPokemonOriginalQueryDetail : SharedBase
- (instancetype)initWithTypes:(NSArray<SharedPokemonOriginalQueryType *> *)types stats:(NSArray<SharedPokemonOriginalQueryStat *> *)stats height:(SharedInt * _Nullable)height weight:(SharedInt * _Nullable)weight moves:(NSArray<SharedPokemonOriginalQueryMove *> *)moves abilities:(NSArray<SharedPokemonOriginalQueryAbility *> *)abilities __attribute__((swift_name("init(types:stats:height:weight:moves:abilities:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalQueryDetail *)doCopyTypes:(NSArray<SharedPokemonOriginalQueryType *> *)types stats:(NSArray<SharedPokemonOriginalQueryStat *> *)stats height:(SharedInt * _Nullable)height weight:(SharedInt * _Nullable)weight moves:(NSArray<SharedPokemonOriginalQueryMove *> *)moves abilities:(NSArray<SharedPokemonOriginalQueryAbility *> *)abilities __attribute__((swift_name("doCopy(types:stats:height:weight:moves:abilities:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSArray<SharedPokemonOriginalQueryAbility *> *abilities __attribute__((swift_name("abilities")));
@property (readonly) SharedInt * _Nullable height __attribute__((swift_name("height")));
@property (readonly) NSArray<SharedPokemonOriginalQueryMove *> *moves __attribute__((swift_name("moves")));
@property (readonly) NSArray<SharedPokemonOriginalQueryStat *> *stats __attribute__((swift_name("stats")));
@property (readonly) NSArray<SharedPokemonOriginalQueryType *> *types __attribute__((swift_name("types")));
@property (readonly) SharedInt * _Nullable weight __attribute__((swift_name("weight")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery.Evolution")))
@interface SharedPokemonOriginalQueryEvolution : SharedBase
- (instancetype)initWithId:(int32_t)id targetLevels:(NSArray<SharedPokemonOriginalQueryTargetLevel *> *)targetLevels __attribute__((swift_name("init(id:targetLevels:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalQueryEvolution *)doCopyId:(int32_t)id targetLevels:(NSArray<SharedPokemonOriginalQueryTargetLevel *> *)targetLevels __attribute__((swift_name("doCopy(id:targetLevels:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t id __attribute__((swift_name("id")));
@property (readonly) NSArray<SharedPokemonOriginalQueryTargetLevel *> *targetLevels __attribute__((swift_name("targetLevels")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery.EvolutionChain")))
@interface SharedPokemonOriginalQueryEvolutionChain : SharedBase
- (instancetype)initWithEvolutions:(NSArray<SharedPokemonOriginalQueryEvolution *> *)evolutions __attribute__((swift_name("init(evolutions:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalQueryEvolutionChain *)doCopyEvolutions:(NSArray<SharedPokemonOriginalQueryEvolution *> *)evolutions __attribute__((swift_name("doCopy(evolutions:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSArray<SharedPokemonOriginalQueryEvolution *> *evolutions __attribute__((swift_name("evolutions")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery.Info")))
@interface SharedPokemonOriginalQueryInfo : SharedBase
- (instancetype)initWithTotal:(SharedPokemonOriginalQueryTotal * _Nullable)total __attribute__((swift_name("init(total:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalQueryInfo *)doCopyTotal:(SharedPokemonOriginalQueryTotal * _Nullable)total __attribute__((swift_name("doCopy(total:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) SharedPokemonOriginalQueryTotal * _Nullable total __attribute__((swift_name("total")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery.Move")))
@interface SharedPokemonOriginalQueryMove : SharedBase
- (instancetype)initWithLevel:(int32_t)level id:(SharedInt * _Nullable)id __attribute__((swift_name("init(level:id:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalQueryMove *)doCopyLevel:(int32_t)level id:(SharedInt * _Nullable)id __attribute__((swift_name("doCopy(level:id:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) SharedInt * _Nullable id __attribute__((swift_name("id")));
@property (readonly) int32_t level __attribute__((swift_name("level")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery.Pokemon")))
@interface SharedPokemonOriginalQueryPokemon : SharedBase
- (instancetype)initWithId:(int32_t)id name:(NSString *)name description:(NSArray<SharedPokemonOriginalQueryDescription *> *)description detail:(NSArray<SharedPokemonOriginalQueryDetail *> *)detail genderRate:(SharedInt * _Nullable)genderRate evolutionChain:(SharedPokemonOriginalQueryEvolutionChain * _Nullable)evolutionChain species:(NSArray<SharedPokemonOriginalQuerySpecies *> *)species generation_id:(SharedInt * _Nullable)generation_id __attribute__((swift_name("init(id:name:description:detail:genderRate:evolutionChain:species:generation_id:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalQueryPokemon *)doCopyId:(int32_t)id name:(NSString *)name description:(NSArray<SharedPokemonOriginalQueryDescription *> *)description detail:(NSArray<SharedPokemonOriginalQueryDetail *> *)detail genderRate:(SharedInt * _Nullable)genderRate evolutionChain:(SharedPokemonOriginalQueryEvolutionChain * _Nullable)evolutionChain species:(NSArray<SharedPokemonOriginalQuerySpecies *> *)species generation_id:(SharedInt * _Nullable)generation_id __attribute__((swift_name("doCopy(id:name:description:detail:genderRate:evolutionChain:species:generation_id:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSArray<SharedPokemonOriginalQueryDescription *> *description_ __attribute__((swift_name("description_")));
@property (readonly) NSArray<SharedPokemonOriginalQueryDetail *> *detail __attribute__((swift_name("detail")));
@property (readonly) SharedPokemonOriginalQueryEvolutionChain * _Nullable evolutionChain __attribute__((swift_name("evolutionChain")));
@property (readonly) SharedInt * _Nullable genderRate __attribute__((swift_name("genderRate")));
@property (readonly) SharedInt * _Nullable generation_id __attribute__((swift_name("generation_id")));
@property (readonly) int32_t id __attribute__((swift_name("id")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) NSArray<SharedPokemonOriginalQuerySpecies *> *species __attribute__((swift_name("species")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery.Species")))
@interface SharedPokemonOriginalQuerySpecies : SharedBase
- (instancetype)initWithGenus:(NSString *)genus __attribute__((swift_name("init(genus:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalQuerySpecies *)doCopyGenus:(NSString *)genus __attribute__((swift_name("doCopy(genus:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *genus __attribute__((swift_name("genus")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery.Stat")))
@interface SharedPokemonOriginalQueryStat : SharedBase
- (instancetype)initWithStat:(SharedPokemonOriginalQueryStat1 * _Nullable)stat baseStat:(int32_t)baseStat __attribute__((swift_name("init(stat:baseStat:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalQueryStat *)doCopyStat:(SharedPokemonOriginalQueryStat1 * _Nullable)stat baseStat:(int32_t)baseStat __attribute__((swift_name("doCopy(stat:baseStat:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t baseStat __attribute__((swift_name("baseStat")));
@property (readonly) SharedPokemonOriginalQueryStat1 * _Nullable stat __attribute__((swift_name("stat")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery.Stat1")))
@interface SharedPokemonOriginalQueryStat1 : SharedBase
- (instancetype)initWithId:(int32_t)id name:(NSString *)name __attribute__((swift_name("init(id:name:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalQueryStat1 *)doCopyId:(int32_t)id name:(NSString *)name __attribute__((swift_name("doCopy(id:name:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t id __attribute__((swift_name("id")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery.TargetLevel")))
@interface SharedPokemonOriginalQueryTargetLevel : SharedBase
- (instancetype)initWithLevel:(SharedInt * _Nullable)level triggerType:(SharedInt * _Nullable)triggerType itemId:(SharedInt * _Nullable)itemId __attribute__((swift_name("init(level:triggerType:itemId:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalQueryTargetLevel *)doCopyLevel:(SharedInt * _Nullable)level triggerType:(SharedInt * _Nullable)triggerType itemId:(SharedInt * _Nullable)itemId __attribute__((swift_name("doCopy(level:triggerType:itemId:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) SharedInt * _Nullable itemId __attribute__((swift_name("itemId")));
@property (readonly) SharedInt * _Nullable level __attribute__((swift_name("level")));
@property (readonly) SharedInt * _Nullable triggerType __attribute__((swift_name("triggerType")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery.Total")))
@interface SharedPokemonOriginalQueryTotal : SharedBase
- (instancetype)initWithCount:(int32_t)count __attribute__((swift_name("init(count:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalQueryTotal *)doCopyCount:(int32_t)count __attribute__((swift_name("doCopy(count:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t count __attribute__((swift_name("count")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery.Type_")))
@interface SharedPokemonOriginalQueryType : SharedBase
- (instancetype)initWithType:(SharedPokemonOriginalQueryType1 * _Nullable)type __attribute__((swift_name("init(type:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalQueryType *)doCopyType:(SharedPokemonOriginalQueryType1 * _Nullable)type __attribute__((swift_name("doCopy(type:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) SharedPokemonOriginalQueryType1 * _Nullable type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery.Type1")))
@interface SharedPokemonOriginalQueryType1 : SharedBase
- (instancetype)initWithName:(NSString *)name __attribute__((swift_name("init(name:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonOriginalQueryType1 *)doCopyName:(NSString *)name __attribute__((swift_name("doCopy(name:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("AbilitiesQuery_ResponseAdapter")))
@interface SharedAbilitiesQuery_ResponseAdapter : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)abilitiesQuery_ResponseAdapter __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedAbilitiesQuery_ResponseAdapter *shared __attribute__((swift_name("shared")));
@end

__attribute__((swift_name("Apollo_apiAdapter")))
@protocol SharedApollo_apiAdapter
@required

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(id _Nullable)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("AbilitiesQuery_ResponseAdapter.Ability")))
@interface SharedAbilitiesQuery_ResponseAdapterAbility : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)ability __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedAbilitiesQuery_ResponseAdapterAbility *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedAbilitiesQueryAbility * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedAbilitiesQueryAbility *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("AbilitiesQuery_ResponseAdapter.Data")))
@interface SharedAbilitiesQuery_ResponseAdapterData : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)data __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedAbilitiesQuery_ResponseAdapterData *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedAbilitiesQueryData * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedAbilitiesQueryData *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("AbilitiesQuery_ResponseAdapter.FlavorText")))
@interface SharedAbilitiesQuery_ResponseAdapterFlavorText : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)flavorText __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedAbilitiesQuery_ResponseAdapterFlavorText *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedAbilitiesQueryFlavorText * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedAbilitiesQueryFlavorText *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ItemsQuery_ResponseAdapter")))
@interface SharedItemsQuery_ResponseAdapter : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)itemsQuery_ResponseAdapter __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedItemsQuery_ResponseAdapter *shared __attribute__((swift_name("shared")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ItemsQuery_ResponseAdapter.Data")))
@interface SharedItemsQuery_ResponseAdapterData : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)data __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedItemsQuery_ResponseAdapterData *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedItemsQueryData * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedItemsQueryData *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ItemsQuery_ResponseAdapter.FlavorText")))
@interface SharedItemsQuery_ResponseAdapterFlavorText : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)flavorText __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedItemsQuery_ResponseAdapterFlavorText *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedItemsQueryFlavorText * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedItemsQueryFlavorText *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ItemsQuery_ResponseAdapter.Info")))
@interface SharedItemsQuery_ResponseAdapterInfo : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)info __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedItemsQuery_ResponseAdapterInfo *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedItemsQueryInfo * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedItemsQueryInfo *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ItemsQuery_ResponseAdapter.Item")))
@interface SharedItemsQuery_ResponseAdapterItem : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)item __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedItemsQuery_ResponseAdapterItem *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedItemsQueryItem * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedItemsQueryItem *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ItemsQuery_ResponseAdapter.Total")))
@interface SharedItemsQuery_ResponseAdapterTotal : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)total __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedItemsQuery_ResponseAdapterTotal *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedItemsQueryTotal * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedItemsQueryTotal *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuery_ResponseAdapter")))
@interface SharedPokemonOriginalMovesQuery_ResponseAdapter : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)pokemonOriginalMovesQuery_ResponseAdapter __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalMovesQuery_ResponseAdapter *shared __attribute__((swift_name("shared")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuery_ResponseAdapter.Category")))
@interface SharedPokemonOriginalMovesQuery_ResponseAdapterCategory : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)category __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalMovesQuery_ResponseAdapterCategory *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalMovesQueryCategory * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalMovesQueryCategory *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuery_ResponseAdapter.Data")))
@interface SharedPokemonOriginalMovesQuery_ResponseAdapterData : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)data __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalMovesQuery_ResponseAdapterData *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalMovesQueryData * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalMovesQueryData *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuery_ResponseAdapter.Description")))
@interface SharedPokemonOriginalMovesQuery_ResponseAdapterDescription : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)description_ __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalMovesQuery_ResponseAdapterDescription *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalMovesQueryDescription * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalMovesQueryDescription *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuery_ResponseAdapter.Info")))
@interface SharedPokemonOriginalMovesQuery_ResponseAdapterInfo : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)info __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalMovesQuery_ResponseAdapterInfo *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalMovesQueryInfo * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalMovesQueryInfo *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuery_ResponseAdapter.Move")))
@interface SharedPokemonOriginalMovesQuery_ResponseAdapterMove : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)move __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalMovesQuery_ResponseAdapterMove *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalMovesQueryMove * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalMovesQueryMove *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuery_ResponseAdapter.Total")))
@interface SharedPokemonOriginalMovesQuery_ResponseAdapterTotal : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)total __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalMovesQuery_ResponseAdapterTotal *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalMovesQueryTotal * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalMovesQueryTotal *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuery_ResponseAdapter.Type_")))
@interface SharedPokemonOriginalMovesQuery_ResponseAdapterType : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)type __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalMovesQuery_ResponseAdapterType *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalMovesQueryType * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalMovesQueryType *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_ResponseAdapter")))
@interface SharedPokemonOriginalQuery_ResponseAdapter : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)pokemonOriginalQuery_ResponseAdapter __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_ResponseAdapter *shared __attribute__((swift_name("shared")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_ResponseAdapter.Ability")))
@interface SharedPokemonOriginalQuery_ResponseAdapterAbility : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)ability __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_ResponseAdapterAbility *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalQueryAbility * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalQueryAbility *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_ResponseAdapter.Data")))
@interface SharedPokemonOriginalQuery_ResponseAdapterData : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)data __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_ResponseAdapterData *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalQueryData * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalQueryData *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_ResponseAdapter.Description")))
@interface SharedPokemonOriginalQuery_ResponseAdapterDescription : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)description_ __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_ResponseAdapterDescription *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalQueryDescription * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalQueryDescription *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_ResponseAdapter.Detail")))
@interface SharedPokemonOriginalQuery_ResponseAdapterDetail : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)detail __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_ResponseAdapterDetail *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalQueryDetail * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalQueryDetail *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_ResponseAdapter.Evolution")))
@interface SharedPokemonOriginalQuery_ResponseAdapterEvolution : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)evolution __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_ResponseAdapterEvolution *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalQueryEvolution * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalQueryEvolution *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_ResponseAdapter.EvolutionChain")))
@interface SharedPokemonOriginalQuery_ResponseAdapterEvolutionChain : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)evolutionChain __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_ResponseAdapterEvolutionChain *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalQueryEvolutionChain * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalQueryEvolutionChain *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_ResponseAdapter.Info")))
@interface SharedPokemonOriginalQuery_ResponseAdapterInfo : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)info __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_ResponseAdapterInfo *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalQueryInfo * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalQueryInfo *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_ResponseAdapter.Move")))
@interface SharedPokemonOriginalQuery_ResponseAdapterMove : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)move __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_ResponseAdapterMove *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalQueryMove * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalQueryMove *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_ResponseAdapter.Pokemon")))
@interface SharedPokemonOriginalQuery_ResponseAdapterPokemon : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)pokemon __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_ResponseAdapterPokemon *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalQueryPokemon * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalQueryPokemon *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_ResponseAdapter.Species")))
@interface SharedPokemonOriginalQuery_ResponseAdapterSpecies : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)species __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_ResponseAdapterSpecies *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalQuerySpecies * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalQuerySpecies *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_ResponseAdapter.Stat")))
@interface SharedPokemonOriginalQuery_ResponseAdapterStat : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)stat __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_ResponseAdapterStat *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalQueryStat * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalQueryStat *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_ResponseAdapter.Stat1")))
@interface SharedPokemonOriginalQuery_ResponseAdapterStat1 : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)stat1 __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_ResponseAdapterStat1 *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalQueryStat1 * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalQueryStat1 *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_ResponseAdapter.TargetLevel")))
@interface SharedPokemonOriginalQuery_ResponseAdapterTargetLevel : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)targetLevel __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_ResponseAdapterTargetLevel *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalQueryTargetLevel * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalQueryTargetLevel *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_ResponseAdapter.Total")))
@interface SharedPokemonOriginalQuery_ResponseAdapterTotal : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)total __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_ResponseAdapterTotal *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalQueryTotal * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalQueryTotal *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_ResponseAdapter.Type_")))
@interface SharedPokemonOriginalQuery_ResponseAdapterType : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)type __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_ResponseAdapterType *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalQueryType * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalQueryType *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_ResponseAdapter.Type1")))
@interface SharedPokemonOriginalQuery_ResponseAdapterType1 : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)type1 __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_ResponseAdapterType1 *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalQueryType1 * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalQueryType1 *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@property (readonly) NSArray<NSString *> *RESPONSE_NAMES __attribute__((swift_name("RESPONSE_NAMES")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuery_VariablesAdapter")))
@interface SharedPokemonOriginalQuery_VariablesAdapter : SharedBase <SharedApollo_apiAdapter>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)pokemonOriginalQuery_VariablesAdapter __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuery_VariablesAdapter *shared __attribute__((swift_name("shared")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedPokemonOriginalQuery * _Nullable)fromJsonReader:(id<SharedApollo_apiJsonReader>)reader customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("fromJson(reader:customScalarAdapters:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)toJsonWriter:(id<SharedApollo_apiJsonWriter>)writer customScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters value:(SharedPokemonOriginalQuery *)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("toJson(writer:customScalarAdapters:value:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ApolloClientProvider")))
@interface SharedApolloClientProvider : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)apolloClientProvider __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedApolloClientProvider *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_runtimeApolloClient *apolloClient __attribute__((swift_name("apolloClient")));
@end

__attribute__((swift_name("Result")))
@interface SharedResult<__covariant R> : SharedBase
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ResultError")))
@interface SharedResultError : SharedResult<SharedKotlinNothing *>
- (instancetype)initWithException:(SharedKotlinException *)exception __attribute__((swift_name("init(exception:)"))) __attribute__((objc_designated_initializer));
- (SharedResultError *)doCopyException:(SharedKotlinException *)exception __attribute__((swift_name("doCopy(exception:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) SharedKotlinException *exception __attribute__((swift_name("exception")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ResultSuccess")))
@interface SharedResultSuccess<__covariant T> : SharedResult<T>
- (instancetype)initWithData:(T _Nullable)data __attribute__((swift_name("init(data:)"))) __attribute__((objc_designated_initializer));
- (SharedResultSuccess<T> *)doCopyData:(T _Nullable)data __attribute__((swift_name("doCopy(data:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) T _Nullable data __attribute__((swift_name("data")));
@end

__attribute__((swift_name("AbilitiesDao")))
@protocol SharedAbilitiesDao
@required

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)deleteAllWithCompletionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("deleteAll(completionHandler:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)findByIdId:(int32_t)id __attribute__((swift_name("findById(id:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)findByIdsIds:(NSArray<SharedInt *> *)ids completionHandler:(void (^)(NSArray<SharedAbility *> * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("findByIds(ids:completionHandler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)findByNameName:(NSString *)name completionHandler:(void (^)(NSArray<SharedAbility *> * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("findByName(name:completionHandler:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)getAll __attribute__((swift_name("getAll()")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)insertItem:(SharedAbility *)item completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("insert(item:completionHandler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)insertAllItem:(SharedKotlinArray<SharedAbility *> *)item completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("insertAll(item:completionHandler:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("AbilitiesDao_Impl")))
@interface SharedAbilitiesDao_Impl : SharedBase <SharedAbilitiesDao>
- (instancetype)initWith__db:(SharedRoom_runtimeRoomDatabase *)__db __attribute__((swift_name("init(__db:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) SharedAbilitiesDao_ImplCompanion *companion __attribute__((swift_name("companion")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)deleteAllWithCompletionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("deleteAll(completionHandler:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)findByIdId:(int32_t)id __attribute__((swift_name("findById(id:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)findByIdsIds:(NSArray<SharedInt *> *)ids completionHandler:(void (^)(NSArray<SharedAbility *> * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("findByIds(ids:completionHandler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)findByNameName:(NSString *)name completionHandler:(void (^)(NSArray<SharedAbility *> * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("findByName(name:completionHandler:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)getAll __attribute__((swift_name("getAll()")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)insertItem:(SharedAbility *)item completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("insert(item:completionHandler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)insertAllItem:(SharedKotlinArray<SharedAbility *> *)item completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("insertAll(item:completionHandler:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("AbilitiesDao_Impl.Companion")))
@interface SharedAbilitiesDao_ImplCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedAbilitiesDao_ImplCompanion *shared __attribute__((swift_name("shared")));
- (NSArray<id<SharedKotlinKClass>> *)getRequiredConverters __attribute__((swift_name("getRequiredConverters()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Converters")))
@interface SharedConverters : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (NSString *)evolutionListToStringList:(NSArray<SharedEvolution *> *)list __attribute__((swift_name("evolutionListToString(list:)")));
- (NSString * _Nullable)listToStringList:(NSArray<NSString *> * _Nullable)list __attribute__((swift_name("listToString(list:)")));
- (NSString *)pokemonAbilityListToStringList:(NSArray<SharedPokemonAbility *> *)list __attribute__((swift_name("pokemonAbilityListToString(list:)")));
- (NSString *)pokemonMoveListToStringList:(NSArray<SharedPokemonMove *> *)list __attribute__((swift_name("pokemonMoveListToString(list:)")));
- (NSArray<SharedEvolution *> *)stringToEvolutionListStr:(NSString *)str __attribute__((swift_name("stringToEvolutionList(str:)")));
- (NSArray<NSString *> * _Nullable)stringToListStr:(NSString * _Nullable)str __attribute__((swift_name("stringToList(str:)")));
- (NSArray<SharedPokemonAbility *> *)stringToPokemonAbilityListStr:(NSString *)str __attribute__((swift_name("stringToPokemonAbilityList(str:)")));
- (NSArray<SharedPokemonMove *> *)stringToPokemonMoveListStr:(NSString *)str __attribute__((swift_name("stringToPokemonMoveList(str:)")));
@end

__attribute__((swift_name("ItemsDao")))
@protocol SharedItemsDao
@required

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)deleteAllWithCompletionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("deleteAll(completionHandler:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)findByIdId:(int32_t)id __attribute__((swift_name("findById(id:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)findByIdsIds:(NSArray<SharedInt *> *)ids completionHandler:(void (^)(NSArray<SharedItem *> * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("findByIds(ids:completionHandler:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)findByNameName:(NSString *)name __attribute__((swift_name("findByName(name:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)getAll __attribute__((swift_name("getAll()")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)insertItem:(SharedItem *)item completionHandler_:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("insert(item:completionHandler_:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)insertAllItem:(SharedKotlinArray<SharedItem *> *)item completionHandler_:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("insertAll(item:completionHandler_:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ItemsDao_Impl")))
@interface SharedItemsDao_Impl : SharedBase <SharedItemsDao>
- (instancetype)initWith__db:(SharedRoom_runtimeRoomDatabase *)__db __attribute__((swift_name("init(__db:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) SharedItemsDao_ImplCompanion *companion __attribute__((swift_name("companion")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)deleteAllWithCompletionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("deleteAll(completionHandler:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)findByIdId:(int32_t)id __attribute__((swift_name("findById(id:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)findByIdsIds:(NSArray<SharedInt *> *)ids completionHandler:(void (^)(NSArray<SharedItem *> * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("findByIds(ids:completionHandler:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)findByNameName:(NSString *)name __attribute__((swift_name("findByName(name:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)getAll __attribute__((swift_name("getAll()")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)insertItem:(SharedItem *)item completionHandler_:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("insert(item:completionHandler_:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)insertAllItem:(SharedKotlinArray<SharedItem *> *)item completionHandler_:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("insertAll(item:completionHandler_:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ItemsDao_Impl.Companion")))
@interface SharedItemsDao_ImplCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedItemsDao_ImplCompanion *shared __attribute__((swift_name("shared")));
- (NSArray<id<SharedKotlinKClass>> *)getRequiredConverters __attribute__((swift_name("getRequiredConverters()")));
@end

__attribute__((swift_name("MovesDao")))
@protocol SharedMovesDao
@required

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)deleteMove:(SharedMove *)move completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("delete(move:completionHandler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)deleteAllWithCompletionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("deleteAll(completionHandler:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)findByIdId:(int32_t)id __attribute__((swift_name("findById(id:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)findByIdsIds:(NSArray<SharedInt *> *)ids completionHandler:(void (^)(NSArray<SharedMove *> * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("findByIds(ids:completionHandler:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)findByNameName:(NSString *)name __attribute__((swift_name("findByName(name:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)getAll __attribute__((swift_name("getAll()")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)insertMove:(SharedMove *)move completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("insert(move:completionHandler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)insertAllMove:(SharedKotlinArray<SharedMove *> *)move completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("insertAll(move:completionHandler:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("MovesDao_Impl")))
@interface SharedMovesDao_Impl : SharedBase <SharedMovesDao>
- (instancetype)initWith__db:(SharedRoom_runtimeRoomDatabase *)__db __attribute__((swift_name("init(__db:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) SharedMovesDao_ImplCompanion *companion __attribute__((swift_name("companion")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)deleteMove:(SharedMove *)move completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("delete(move:completionHandler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)deleteAllWithCompletionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("deleteAll(completionHandler:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)findByIdId:(int32_t)id __attribute__((swift_name("findById(id:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)findByIdsIds:(NSArray<SharedInt *> *)ids completionHandler:(void (^)(NSArray<SharedMove *> * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("findByIds(ids:completionHandler:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)findByNameName:(NSString *)name __attribute__((swift_name("findByName(name:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)getAll __attribute__((swift_name("getAll()")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)insertMove:(SharedMove *)move completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("insert(move:completionHandler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)insertAllMove:(SharedKotlinArray<SharedMove *> *)move completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("insertAll(move:completionHandler:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("MovesDao_Impl.Companion")))
@interface SharedMovesDao_ImplCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedMovesDao_ImplCompanion *shared __attribute__((swift_name("shared")));
- (NSArray<id<SharedKotlinKClass>> *)getRequiredConverters __attribute__((swift_name("getRequiredConverters()")));
@end

__attribute__((swift_name("Room_runtimeRoomDatabase")))
@interface SharedRoom_runtimeRoomDatabase : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)close __attribute__((swift_name("close()")));

/**
 * @note annotations
 *   androidx.annotation.RestrictTo(value=[Scope.LIBRARY_GROUP_PREFIX])
*/
- (NSArray<SharedRoom_runtimeMigration *> *)createAutoMigrationsAutoMigrationSpecs:(NSDictionary<id<SharedKotlinKClass>, id<SharedRoom_runtimeAutoMigrationSpec>> *)autoMigrationSpecs __attribute__((swift_name("createAutoMigrations(autoMigrationSpecs:)")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (SharedRoom_runtimeInvalidationTracker *)createInvalidationTracker __attribute__((swift_name("createInvalidationTracker()")));

/**
 * @note annotations
 *   androidx.annotation.RestrictTo(value=[Scope.LIBRARY_GROUP_PREFIX])
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (id<SharedRoom_runtimeRoomOpenDelegateMarker>)createOpenDelegate __attribute__((swift_name("createOpenDelegate()")));

/**
 * @note annotations
 *   androidx.annotation.RestrictTo(value=[Scope.LIBRARY_GROUP])
*/
- (id<SharedKotlinx_coroutines_coreCoroutineScope>)getCoroutineScope __attribute__((swift_name("getCoroutineScope()")));

/**
 * @note annotations
 *   androidx.annotation.RestrictTo(value=[Scope.LIBRARY_GROUP_PREFIX])
*/
- (NSSet<id<SharedKotlinKClass>> *)getRequiredAutoMigrationSpecClasses __attribute__((swift_name("getRequiredAutoMigrationSpecClasses()")));

/**
 * @note annotations
 *   androidx.annotation.RestrictTo(value=[Scope.LIBRARY_GROUP_PREFIX])
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (NSDictionary<id<SharedKotlinKClass>, NSArray<id<SharedKotlinKClass>> *> *)getRequiredTypeConverterClasses __attribute__((swift_name("getRequiredTypeConverterClasses()")));

/**
 * @note annotations
 *   androidx.annotation.RestrictTo(value=[Scope.LIBRARY_GROUP_PREFIX])
*/
- (id)getTypeConverterKlass:(id<SharedKotlinKClass>)klass __attribute__((swift_name("getTypeConverter(klass:)")));

/**
 * @note annotations
 *   androidx.annotation.RestrictTo(value=[Scope.LIBRARY_GROUP_PREFIX])
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (void)internalInitInvalidationTrackerConnection:(id<SharedSqliteSQLiteConnection>)connection __attribute__((swift_name("internalInitInvalidationTracker(connection:)")));
@property (readonly) SharedRoom_runtimeInvalidationTracker *invalidationTracker __attribute__((swift_name("invalidationTracker")));
@end

__attribute__((swift_name("PokedexerDatabase")))
@interface SharedPokedexerDatabase : SharedRoom_runtimeRoomDatabase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (id<SharedAbilitiesDao>)abilitiesDao __attribute__((swift_name("abilitiesDao()")));
- (id<SharedItemsDao>)itemsDao __attribute__((swift_name("itemsDao()")));
- (id<SharedMovesDao>)movesDao __attribute__((swift_name("movesDao()")));
- (id<SharedPokemonDao>)pokemonDao __attribute__((swift_name("pokemonDao()")));
@end

__attribute__((swift_name("Room_runtimeRoomDatabaseConstructor")))
@protocol SharedRoom_runtimeRoomDatabaseConstructor
@required
- (SharedRoom_runtimeRoomDatabase *)initialize __attribute__((swift_name("initialize()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokedexerDatabaseConstructor")))
@interface SharedPokedexerDatabaseConstructor : SharedBase <SharedRoom_runtimeRoomDatabaseConstructor>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)pokedexerDatabaseConstructor __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokedexerDatabaseConstructor *shared __attribute__((swift_name("shared")));
- (SharedPokedexerDatabase *)initialize __attribute__((swift_name("initialize()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokedexerDatabase_Impl")))
@interface SharedPokedexerDatabase_Impl : SharedPokedexerDatabase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (id<SharedAbilitiesDao>)abilitiesDao __attribute__((swift_name("abilitiesDao()")));
- (NSArray<SharedRoom_runtimeMigration *> *)createAutoMigrationsAutoMigrationSpecs:(NSDictionary<id<SharedKotlinKClass>, id<SharedRoom_runtimeAutoMigrationSpec>> *)autoMigrationSpecs __attribute__((swift_name("createAutoMigrations(autoMigrationSpecs:)")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (SharedRoom_runtimeInvalidationTracker *)createInvalidationTracker __attribute__((swift_name("createInvalidationTracker()")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (SharedRoom_runtimeRoomOpenDelegate *)createOpenDelegate __attribute__((swift_name("createOpenDelegate()")));
- (NSSet<id<SharedKotlinKClass>> *)getRequiredAutoMigrationSpecClasses __attribute__((swift_name("getRequiredAutoMigrationSpecClasses()")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (NSDictionary<id<SharedKotlinKClass>, NSArray<id<SharedKotlinKClass>> *> *)getRequiredTypeConverterClasses __attribute__((swift_name("getRequiredTypeConverterClasses()")));
- (id<SharedItemsDao>)itemsDao __attribute__((swift_name("itemsDao()")));
- (id<SharedMovesDao>)movesDao __attribute__((swift_name("movesDao()")));
- (id<SharedPokemonDao>)pokemonDao __attribute__((swift_name("pokemonDao()")));
@end

__attribute__((swift_name("PokemonDao")))
@protocol SharedPokemonDao
@required

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)deletePokemon:(SharedPokemon *)pokemon completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("delete(pokemon:completionHandler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)deleteAllWithCompletionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("deleteAll(completionHandler:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)findByIdId:(int32_t)id __attribute__((swift_name("findById(id:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)findByIdsIds:(NSArray<SharedInt *> *)ids __attribute__((swift_name("findByIds(ids:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)findByNameName:(NSString *)name __attribute__((swift_name("findByName(name:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)getAllWithCompletionHandler:(void (^)(NSArray<SharedPokemon *> * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("getAll(completionHandler:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)getAllByGenerationGenerationId:(int32_t)generationId __attribute__((swift_name("getAllByGeneration(generationId:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)getAllFlow __attribute__((swift_name("getAllFlow()")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)insertPokemon:(SharedPokemon *)pokemon completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("insert(pokemon:completionHandler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)insertAllPokemon:(SharedKotlinArray<SharedPokemon *> *)pokemon completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("insertAll(pokemon:completionHandler:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonDao_Impl")))
@interface SharedPokemonDao_Impl : SharedBase <SharedPokemonDao>
- (instancetype)initWith__db:(SharedRoom_runtimeRoomDatabase *)__db __attribute__((swift_name("init(__db:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) SharedPokemonDao_ImplCompanion *companion __attribute__((swift_name("companion")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)deletePokemon:(SharedPokemon *)pokemon completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("delete(pokemon:completionHandler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)deleteAllWithCompletionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("deleteAll(completionHandler:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)findByIdId:(int32_t)id __attribute__((swift_name("findById(id:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)findByIdsIds:(NSArray<SharedInt *> *)ids __attribute__((swift_name("findByIds(ids:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)findByNameName:(NSString *)name __attribute__((swift_name("findByName(name:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)getAllWithCompletionHandler:(void (^)(NSArray<SharedPokemon *> * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("getAll(completionHandler:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)getAllByGenerationGenerationId:(int32_t)generationId __attribute__((swift_name("getAllByGeneration(generationId:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)getAllFlow __attribute__((swift_name("getAllFlow()")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)insertPokemon:(SharedPokemon *)pokemon completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("insert(pokemon:completionHandler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)insertAllPokemon:(SharedKotlinArray<SharedPokemon *> *)pokemon completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("insertAll(pokemon:completionHandler:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonDao_Impl.Companion")))
@interface SharedPokemonDao_ImplCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonDao_ImplCompanion *shared __attribute__((swift_name("shared")));
- (NSArray<id<SharedKotlinKClass>> *)getRequiredConverters __attribute__((swift_name("getRequiredConverters()")));
@end

__attribute__((swift_name("FavoritesStore")))
@protocol SharedFavoritesStore
@required

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)getFavoritesWithCompletionHandler:(void (^)(NSArray<SharedInt *> * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("getFavorites(completionHandler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)toggleFavoritePokemonId:(int32_t)pokemonId completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("toggleFavorite(pokemonId:completionHandler:)")));
@property (readonly) id<SharedKotlinx_coroutines_coreFlow> favoritesFlow __attribute__((swift_name("favoritesFlow")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UserDefaultsFavoritesStore")))
@interface SharedUserDefaultsFavoritesStore : SharedBase <SharedFavoritesStore>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)getFavoritesWithCompletionHandler:(void (^)(NSArray<SharedInt *> * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("getFavorites(completionHandler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)toggleFavoritePokemonId:(int32_t)pokemonId completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("toggleFavorite(pokemonId:completionHandler:)")));
@property (readonly) id<SharedKotlinx_coroutines_coreFlow> favoritesFlow __attribute__((swift_name("favoritesFlow")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ability")))
@interface SharedAbility : SharedBase
- (instancetype)initWithId:(int32_t)id name:(NSString *)name description:(NSString *)description __attribute__((swift_name("init(id:name:description:)"))) __attribute__((objc_designated_initializer));
- (SharedAbility *)doCopyId:(int32_t)id name:(NSString *)name description:(NSString *)description __attribute__((swift_name("doCopy(id:name:description:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *description_ __attribute__((swift_name("description_")));
@property (readonly) int32_t id __attribute__((swift_name("id")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Evolution")))
@interface SharedEvolution : SharedBase
- (instancetype)initWithId:(int32_t)id targetLevel:(int32_t)targetLevel trigger:(SharedEvolutionTrigger *)trigger itemId:(int32_t)itemId __attribute__((swift_name("init(id:targetLevel:trigger:itemId:)"))) __attribute__((objc_designated_initializer));
- (SharedEvolution *)doCopyId:(int32_t)id targetLevel:(int32_t)targetLevel trigger:(SharedEvolutionTrigger *)trigger itemId:(int32_t)itemId __attribute__((swift_name("doCopy(id:targetLevel:trigger:itemId:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t id __attribute__((swift_name("id")));
@property (readonly) int32_t itemId __attribute__((swift_name("itemId")));
@property (readonly) int32_t targetLevel __attribute__((swift_name("targetLevel")));
@property (readonly) SharedEvolutionTrigger *trigger __attribute__((swift_name("trigger")));
@end

__attribute__((swift_name("KotlinComparable")))
@protocol SharedKotlinComparable
@required
- (int32_t)compareToOther:(id _Nullable)other __attribute__((swift_name("compareTo(other:)")));
@end

__attribute__((swift_name("KotlinEnum")))
@interface SharedKotlinEnum<E> : SharedBase <SharedKotlinComparable>
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) SharedKotlinEnumCompanion *companion __attribute__((swift_name("companion")));
- (int32_t)compareToOther:(E)other __attribute__((swift_name("compareTo(other:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly, getter=name_) NSString *name __attribute__((swift_name("name")));
@property (readonly) int32_t ordinal __attribute__((swift_name("ordinal")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("EvolutionTrigger")))
@interface SharedEvolutionTrigger : SharedKotlinEnum<SharedEvolutionTrigger *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly, getter=companion) SharedEvolutionTriggerCompanion *companion __attribute__((swift_name("companion")));
@property (class, readonly) SharedEvolutionTrigger *levelup __attribute__((swift_name("levelup")));
@property (class, readonly) SharedEvolutionTrigger *useitem __attribute__((swift_name("useitem")));
@property (class, readonly) SharedEvolutionTrigger *trade __attribute__((swift_name("trade")));
+ (SharedKotlinArray<SharedEvolutionTrigger *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<SharedEvolutionTrigger *> *entries __attribute__((swift_name("entries")));
@property (readonly) int32_t value __attribute__((swift_name("value")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("EvolutionTrigger.Companion")))
@interface SharedEvolutionTriggerCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedEvolutionTriggerCompanion *shared __attribute__((swift_name("shared")));
- (SharedEvolutionTrigger *)fromIntValue:(int32_t)value __attribute__((swift_name("fromInt(value:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Generation")))
@interface SharedGeneration : SharedKotlinEnum<SharedGeneration *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly, getter=companion) SharedGenerationCompanion *companion __attribute__((swift_name("companion")));
@property (class, readonly) SharedGeneration *i __attribute__((swift_name("i")));
@property (class, readonly) SharedGeneration *ii __attribute__((swift_name("ii")));
@property (class, readonly) SharedGeneration *iii __attribute__((swift_name("iii")));
@property (class, readonly) SharedGeneration *iv __attribute__((swift_name("iv")));
@property (class, readonly) SharedGeneration *v __attribute__((swift_name("v")));
@property (class, readonly) SharedGeneration *vi __attribute__((swift_name("vi")));
@property (class, readonly) SharedGeneration *vii __attribute__((swift_name("vii")));
@property (class, readonly) SharedGeneration *viii __attribute__((swift_name("viii")));
@property (class, readonly) SharedGeneration *ix __attribute__((swift_name("ix")));
+ (SharedKotlinArray<SharedGeneration *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<SharedGeneration *> *entries __attribute__((swift_name("entries")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly, getter=id_) int32_t id __attribute__((swift_name("id")));
@property (readonly) NSString *romanNumeral __attribute__((swift_name("romanNumeral")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Generation.Companion")))
@interface SharedGenerationCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedGenerationCompanion *shared __attribute__((swift_name("shared")));
- (SharedGeneration * _Nullable)fromIdId:(int32_t)id __attribute__((swift_name("fromId(id:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Item")))
@interface SharedItem : SharedBase
- (instancetype)initWithId:(int32_t)id name:(NSString *)name description:(NSString *)description sprite:(NSString *)sprite __attribute__((swift_name("init(id:name:description:sprite:)"))) __attribute__((objc_designated_initializer));
- (SharedItem *)doCopyId:(int32_t)id name:(NSString *)name description:(NSString *)description sprite:(NSString *)sprite __attribute__((swift_name("doCopy(id:name:description:sprite:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *description_ __attribute__((swift_name("description_")));
@property (readonly) int32_t id __attribute__((swift_name("id")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) NSString *sprite __attribute__((swift_name("sprite")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Move")))
@interface SharedMove : SharedBase
- (instancetype)initWithId:(int32_t)id name:(NSString *)name description:(NSString *)description category:(NSString *)category type:(NSString *)type pp:(int32_t)pp power:(SharedInt * _Nullable)power accuracy:(SharedInt * _Nullable)accuracy __attribute__((swift_name("init(id:name:description:category:type:pp:power:accuracy:)"))) __attribute__((objc_designated_initializer));
- (SharedMove *)doCopyId:(int32_t)id name:(NSString *)name description:(NSString *)description category:(NSString *)category type:(NSString *)type pp:(int32_t)pp power:(SharedInt * _Nullable)power accuracy:(SharedInt * _Nullable)accuracy __attribute__((swift_name("doCopy(id:name:description:category:type:pp:power:accuracy:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) SharedInt * _Nullable accuracy __attribute__((swift_name("accuracy")));
@property (readonly) NSString *category __attribute__((swift_name("category")));
@property (readonly) NSString *description_ __attribute__((swift_name("description_")));
@property (readonly) int32_t id __attribute__((swift_name("id")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) SharedInt * _Nullable power __attribute__((swift_name("power")));
@property (readonly) int32_t pp __attribute__((swift_name("pp")));
@property (readonly) NSString *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("MoveCategory")))
@interface SharedMoveCategory : SharedKotlinEnum<SharedMoveCategory *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) SharedMoveCategory *physical __attribute__((swift_name("physical")));
@property (class, readonly) SharedMoveCategory *special __attribute__((swift_name("special")));
@property (class, readonly) SharedMoveCategory *status __attribute__((swift_name("status")));
+ (SharedKotlinArray<SharedMoveCategory *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<SharedMoveCategory *> *entries __attribute__((swift_name("entries")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon")))
@interface SharedPokemon : SharedBase
- (instancetype)initWithId:(int32_t)id name:(NSString *)name description:(NSString *)description typeOfPokemon:(NSArray<NSString *> *)typeOfPokemon category:(NSString *)category image:(int32_t)image height:(double)height weight:(double)weight genderRate:(int32_t)genderRate generationId:(int32_t)generationId hp:(int32_t)hp attack:(int32_t)attack defense:(int32_t)defense specialAttack:(int32_t)specialAttack specialDefense:(int32_t)specialDefense speed:(int32_t)speed evolutionChain:(NSArray<SharedEvolution *> *)evolutionChain movesList:(NSArray<SharedPokemonMove *> *)movesList abilitiesList:(NSArray<SharedPokemonAbility *> *)abilitiesList __attribute__((swift_name("init(id:name:description:typeOfPokemon:category:image:height:weight:genderRate:generationId:hp:attack:defense:specialAttack:specialDefense:speed:evolutionChain:movesList:abilitiesList:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemon *)doCopyId:(int32_t)id name:(NSString *)name description:(NSString *)description typeOfPokemon:(NSArray<NSString *> *)typeOfPokemon category:(NSString *)category image:(int32_t)image height:(double)height weight:(double)weight genderRate:(int32_t)genderRate generationId:(int32_t)generationId hp:(int32_t)hp attack:(int32_t)attack defense:(int32_t)defense specialAttack:(int32_t)specialAttack specialDefense:(int32_t)specialDefense speed:(int32_t)speed evolutionChain:(NSArray<SharedEvolution *> *)evolutionChain movesList:(NSArray<SharedPokemonMove *> *)movesList abilitiesList:(NSArray<SharedPokemonAbility *> *)abilitiesList __attribute__((swift_name("doCopy(id:name:description:typeOfPokemon:category:image:height:weight:genderRate:generationId:hp:attack:defense:specialAttack:specialDefense:speed:evolutionChain:movesList:abilitiesList:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSArray<SharedPokemonAbility *> *abilitiesList __attribute__((swift_name("abilitiesList")));
@property (readonly) int32_t attack __attribute__((swift_name("attack")));
@property (readonly) NSString *category __attribute__((swift_name("category")));
@property (readonly) int32_t defense __attribute__((swift_name("defense")));
@property (readonly) NSString *description_ __attribute__((swift_name("description_")));
@property (readonly) NSArray<SharedEvolution *> *evolutionChain __attribute__((swift_name("evolutionChain")));
@property (readonly) int32_t genderRate __attribute__((swift_name("genderRate")));
@property (readonly) int32_t generationId __attribute__((swift_name("generationId")));
@property (readonly) double height __attribute__((swift_name("height")));
@property (readonly) int32_t hp __attribute__((swift_name("hp")));
@property (readonly) int32_t id __attribute__((swift_name("id")));
@property (readonly) int32_t image __attribute__((swift_name("image")));
@property (readonly) NSArray<SharedPokemonMove *> *movesList __attribute__((swift_name("movesList")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) int32_t specialAttack __attribute__((swift_name("specialAttack")));
@property (readonly) int32_t specialDefense __attribute__((swift_name("specialDefense")));
@property (readonly) int32_t speed __attribute__((swift_name("speed")));
@property (readonly) NSArray<NSString *> *typeOfPokemon __attribute__((swift_name("typeOfPokemon")));
@property (readonly) double weight __attribute__((swift_name("weight")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonAbility")))
@interface SharedPokemonAbility : SharedBase
- (instancetype)initWithId:(int32_t)id isHidden:(BOOL)isHidden __attribute__((swift_name("init(id:isHidden:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonAbility *)doCopyId:(int32_t)id isHidden:(BOOL)isHidden __attribute__((swift_name("doCopy(id:isHidden:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t id __attribute__((swift_name("id")));
@property (readonly) BOOL isHidden __attribute__((swift_name("isHidden")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonMove")))
@interface SharedPokemonMove : SharedBase
- (instancetype)initWithId:(int32_t)id targetLevel:(int32_t)targetLevel __attribute__((swift_name("init(id:targetLevel:)"))) __attribute__((objc_designated_initializer));
- (SharedPokemonMove *)doCopyId:(int32_t)id targetLevel:(int32_t)targetLevel __attribute__((swift_name("doCopy(id:targetLevel:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t id __attribute__((swift_name("id")));
@property (readonly) int32_t targetLevel __attribute__((swift_name("targetLevel")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Type")))
@interface SharedType : SharedKotlinEnum<SharedType *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) SharedType *bug __attribute__((swift_name("bug")));
@property (class, readonly) SharedType *dark __attribute__((swift_name("dark")));
@property (class, readonly) SharedType *dragon __attribute__((swift_name("dragon")));
@property (class, readonly) SharedType *electric __attribute__((swift_name("electric")));
@property (class, readonly) SharedType *fairy __attribute__((swift_name("fairy")));
@property (class, readonly) SharedType *fighting __attribute__((swift_name("fighting")));
@property (class, readonly) SharedType *fire __attribute__((swift_name("fire")));
@property (class, readonly) SharedType *flying __attribute__((swift_name("flying")));
@property (class, readonly) SharedType *ghost __attribute__((swift_name("ghost")));
@property (class, readonly) SharedType *grass __attribute__((swift_name("grass")));
@property (class, readonly) SharedType *ground __attribute__((swift_name("ground")));
@property (class, readonly) SharedType *ice __attribute__((swift_name("ice")));
@property (class, readonly) SharedType *normal __attribute__((swift_name("normal")));
@property (class, readonly) SharedType *poison __attribute__((swift_name("poison")));
@property (class, readonly) SharedType *psychic __attribute__((swift_name("psychic")));
@property (class, readonly) SharedType *rock __attribute__((swift_name("rock")));
@property (class, readonly) SharedType *steel __attribute__((swift_name("steel")));
@property (class, readonly) SharedType *water __attribute__((swift_name("water")));
+ (SharedKotlinArray<SharedType *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<SharedType *> *entries __attribute__((swift_name("entries")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("AbilitiesQuerySelections")))
@interface SharedAbilitiesQuerySelections : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)abilitiesQuerySelections __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedAbilitiesQuerySelections *shared __attribute__((swift_name("shared")));
@property (readonly) NSArray<SharedApollo_apiCompiledSelection *> *__root __attribute__((swift_name("__root")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ItemsQuerySelections")))
@interface SharedItemsQuerySelections : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)itemsQuerySelections __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedItemsQuerySelections *shared __attribute__((swift_name("shared")));
@property (readonly) NSArray<SharedApollo_apiCompiledSelection *> *__root __attribute__((swift_name("__root")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalMovesQuerySelections")))
@interface SharedPokemonOriginalMovesQuerySelections : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)pokemonOriginalMovesQuerySelections __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalMovesQuerySelections *shared __attribute__((swift_name("shared")));
@property (readonly) NSArray<SharedApollo_apiCompiledSelection *> *__root __attribute__((swift_name("__root")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokemonOriginalQuerySelections")))
@interface SharedPokemonOriginalQuerySelections : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)pokemonOriginalQuerySelections __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemonOriginalQuerySelections *shared __attribute__((swift_name("shared")));
@property (readonly) NSArray<SharedApollo_apiCompiledSelection *> *__root __attribute__((swift_name("__root")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("GraphQLBoolean")))
@interface SharedGraphQLBoolean : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedGraphQLBooleanCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("GraphQLBoolean.Companion")))
@interface SharedGraphQLBooleanCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedGraphQLBooleanCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiCustomScalarType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("GraphQLFloat")))
@interface SharedGraphQLFloat : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedGraphQLFloatCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("GraphQLFloat.Companion")))
@interface SharedGraphQLFloatCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedGraphQLFloatCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiCustomScalarType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("GraphQLID")))
@interface SharedGraphQLID : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedGraphQLIDCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("GraphQLID.Companion")))
@interface SharedGraphQLIDCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedGraphQLIDCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiCustomScalarType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("GraphQLInt")))
@interface SharedGraphQLInt : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedGraphQLIntCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("GraphQLInt.Companion")))
@interface SharedGraphQLIntCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedGraphQLIntCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiCustomScalarType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("GraphQLString")))
@interface SharedGraphQLString : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedGraphQLStringCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("GraphQLString.Companion")))
@interface SharedGraphQLStringCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedGraphQLStringCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiCustomScalarType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_ability")))
@interface SharedPokemon_v2_ability : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_abilityCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_ability.Companion")))
@interface SharedPokemon_v2_abilityCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_abilityCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_abilityflavortext")))
@interface SharedPokemon_v2_abilityflavortext : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_abilityflavortextCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_abilityflavortext.Companion")))
@interface SharedPokemon_v2_abilityflavortextCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_abilityflavortextCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_evolutionchain")))
@interface SharedPokemon_v2_evolutionchain : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_evolutionchainCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_evolutionchain.Companion")))
@interface SharedPokemon_v2_evolutionchainCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_evolutionchainCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_item")))
@interface SharedPokemon_v2_item : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_itemCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_item.Companion")))
@interface SharedPokemon_v2_itemCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_itemCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_item_aggregate")))
@interface SharedPokemon_v2_item_aggregate : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_item_aggregateCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_item_aggregate.Companion")))
@interface SharedPokemon_v2_item_aggregateCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_item_aggregateCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_item_aggregate_fields")))
@interface SharedPokemon_v2_item_aggregate_fields : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_item_aggregate_fieldsCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_item_aggregate_fields.Companion")))
@interface SharedPokemon_v2_item_aggregate_fieldsCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_item_aggregate_fieldsCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_itemflavortext")))
@interface SharedPokemon_v2_itemflavortext : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_itemflavortextCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_itemflavortext.Companion")))
@interface SharedPokemon_v2_itemflavortextCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_itemflavortextCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_move")))
@interface SharedPokemon_v2_move : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_moveCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_move.Companion")))
@interface SharedPokemon_v2_moveCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_moveCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_move_aggregate")))
@interface SharedPokemon_v2_move_aggregate : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_move_aggregateCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_move_aggregate.Companion")))
@interface SharedPokemon_v2_move_aggregateCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_move_aggregateCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_move_aggregate_fields")))
@interface SharedPokemon_v2_move_aggregate_fields : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_move_aggregate_fieldsCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_move_aggregate_fields.Companion")))
@interface SharedPokemon_v2_move_aggregate_fieldsCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_move_aggregate_fieldsCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_movedamageclass")))
@interface SharedPokemon_v2_movedamageclass : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_movedamageclassCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_movedamageclass.Companion")))
@interface SharedPokemon_v2_movedamageclassCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_movedamageclassCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_moveflavortext")))
@interface SharedPokemon_v2_moveflavortext : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_moveflavortextCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_moveflavortext.Companion")))
@interface SharedPokemon_v2_moveflavortextCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_moveflavortextCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemon")))
@interface SharedPokemon_v2_pokemon : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_pokemonCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemon.Companion")))
@interface SharedPokemon_v2_pokemonCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_pokemonCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonability")))
@interface SharedPokemon_v2_pokemonability : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_pokemonabilityCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonability.Companion")))
@interface SharedPokemon_v2_pokemonabilityCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_pokemonabilityCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonevolution")))
@interface SharedPokemon_v2_pokemonevolution : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_pokemonevolutionCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonevolution.Companion")))
@interface SharedPokemon_v2_pokemonevolutionCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_pokemonevolutionCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonmove")))
@interface SharedPokemon_v2_pokemonmove : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_pokemonmoveCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonmove.Companion")))
@interface SharedPokemon_v2_pokemonmoveCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_pokemonmoveCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonspecies")))
@interface SharedPokemon_v2_pokemonspecies : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_pokemonspeciesCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonspecies.Companion")))
@interface SharedPokemon_v2_pokemonspeciesCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_pokemonspeciesCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonspecies_aggregate")))
@interface SharedPokemon_v2_pokemonspecies_aggregate : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_pokemonspecies_aggregateCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonspecies_aggregate.Companion")))
@interface SharedPokemon_v2_pokemonspecies_aggregateCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_pokemonspecies_aggregateCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonspecies_aggregate_fields")))
@interface SharedPokemon_v2_pokemonspecies_aggregate_fields : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_pokemonspecies_aggregate_fieldsCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonspecies_aggregate_fields.Companion")))
@interface SharedPokemon_v2_pokemonspecies_aggregate_fieldsCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_pokemonspecies_aggregate_fieldsCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonspeciesflavortext")))
@interface SharedPokemon_v2_pokemonspeciesflavortext : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_pokemonspeciesflavortextCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonspeciesflavortext.Companion")))
@interface SharedPokemon_v2_pokemonspeciesflavortextCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_pokemonspeciesflavortextCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonspeciesname")))
@interface SharedPokemon_v2_pokemonspeciesname : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_pokemonspeciesnameCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonspeciesname.Companion")))
@interface SharedPokemon_v2_pokemonspeciesnameCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_pokemonspeciesnameCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonstat")))
@interface SharedPokemon_v2_pokemonstat : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_pokemonstatCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemonstat.Companion")))
@interface SharedPokemon_v2_pokemonstatCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_pokemonstatCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemontype")))
@interface SharedPokemon_v2_pokemontype : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_pokemontypeCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_pokemontype.Companion")))
@interface SharedPokemon_v2_pokemontypeCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_pokemontypeCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_stat")))
@interface SharedPokemon_v2_stat : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_statCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_stat.Companion")))
@interface SharedPokemon_v2_statCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_statCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_type")))
@interface SharedPokemon_v2_type : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedPokemon_v2_typeCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Pokemon_v2_type.Companion")))
@interface SharedPokemon_v2_typeCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedPokemon_v2_typeCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Query_root")))
@interface SharedQuery_root : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedQuery_rootCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Query_root.Companion")))
@interface SharedQuery_rootCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedQuery_rootCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiObjectType *type __attribute__((swift_name("type")));
@end

@interface SharedAbilitiesQueryAbility (Extensions)
- (SharedAbility *)toDomainModel __attribute__((swift_name("toDomainModel()")));
@end

@interface SharedItemsQueryItem (Extensions)
- (SharedItem *)toDomainModel __attribute__((swift_name("toDomainModel()")));
@end

@interface SharedPokedexerSDK (Extensions)
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(NSArray<SharedAbility *> *, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))getAbilitiesByIdsIds:(NSArray<SharedInt *> *)ids __attribute__((swift_name("getAbilitiesByIds(ids:)")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(SharedAbility * _Nullable, SharedKotlinUnit *(^)(void), SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError * _Nullable, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))getAbilityByIdId:(int32_t)id __attribute__((swift_name("getAbilityById(id:)")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(NSArray<SharedAbility *> *, SharedKotlinUnit *(^)(void), SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError * _Nullable, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))getAllAbilities __attribute__((swift_name("getAllAbilities()")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(NSArray<SharedItem *> *, SharedKotlinUnit *(^)(void), SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError * _Nullable, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))getAllItems __attribute__((swift_name("getAllItems()")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(NSArray<SharedMove *> *, SharedKotlinUnit *(^)(void), SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError * _Nullable, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))getAllMoves __attribute__((swift_name("getAllMoves()")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(NSArray<SharedPokemon *> *, SharedKotlinUnit *(^)(void), SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError * _Nullable, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))getAllPokemon __attribute__((swift_name("getAllPokemon()")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(NSArray<SharedPokemon *> *, SharedKotlinUnit *(^)(void), SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError * _Nullable, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))getFavoritePokemon __attribute__((swift_name("getFavoritePokemon()")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(NSArray<SharedInt *> *, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))getFavorites __attribute__((swift_name("getFavorites()")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(SharedItem * _Nullable, SharedKotlinUnit *(^)(void), SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError * _Nullable, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))getItemByIdId:(int32_t)id __attribute__((swift_name("getItemById(id:)")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(SharedMove * _Nullable, SharedKotlinUnit *(^)(void), SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError * _Nullable, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))getMoveByIdId:(int32_t)id __attribute__((swift_name("getMoveById(id:)")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(NSArray<SharedMove *> *, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))getMovesByIdsIds:(NSArray<SharedInt *> *)ids __attribute__((swift_name("getMovesByIds(ids:)")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(NSArray<SharedPokemon *> *, SharedKotlinUnit *(^)(void), SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError * _Nullable, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))getPokemonByGenerationGenerationId:(int32_t)generationId __attribute__((swift_name("getPokemonByGeneration(generationId:)")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(SharedPokemon * _Nullable, SharedKotlinUnit *(^)(void), SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError * _Nullable, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))getPokemonByIdId:(int32_t)id __attribute__((swift_name("getPokemonById(id:)")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(NSArray<SharedAbility *> *, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))loadAbilities __attribute__((swift_name("loadAbilities()")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(NSArray<SharedItem *> *, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))loadItems __attribute__((swift_name("loadItems()")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(NSArray<SharedMove *> *, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))loadMoves __attribute__((swift_name("loadMoves()")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(NSArray<SharedPokemon *> *, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))loadPokemonForGenerationGenerationId:(int32_t)generationId __attribute__((swift_name("loadPokemonForGeneration(generationId:)")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(NSArray<SharedPokemon *> *, SharedKotlinUnit *(^)(void), SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError * _Nullable, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))searchPokemonName:(NSString *)name __attribute__((swift_name("searchPokemon(name:)")));
- (SharedKotlinUnit *(^(^)(SharedKotlinUnit *(^)(SharedKotlinUnit *, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void))toggleFavoritePokemonId:(int32_t)pokemonId __attribute__((swift_name("toggleFavorite(pokemonId:)")));
@property (readonly) SharedKotlinUnit *(^(^favoritesFlow)(SharedKotlinUnit *(^)(NSArray<SharedInt *> *, SharedKotlinUnit *(^)(void), SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError * _Nullable, SharedKotlinUnit *), SharedKotlinUnit *(^)(NSError *, SharedKotlinUnit *)))(void) __attribute__((swift_name("favoritesFlow")));
@end

@interface SharedPokemonOriginalMovesQueryMove (Extensions)
- (SharedMove *)toDomainModel __attribute__((swift_name("toDomainModel()")));
@end

@interface SharedPokemonOriginalQueryPokemon (Extensions)
- (SharedPokemon *)toDomainModelGenerationId:(int32_t)generationId __attribute__((swift_name("toDomainModel(generationId:)")));
@end

@interface SharedResult (Extensions)
- (id _Nullable)dataOrThrow __attribute__((swift_name("dataOrThrow()")));
- (id _Nullable)successOrFallback:(id _Nullable)fallback __attribute__((swift_name("successOr(fallback:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DatabaseBuilder_iosKt")))
@interface SharedDatabaseBuilder_iosKt : SharedBase
+ (SharedRoom_runtimeRoomDatabaseBuilder<SharedPokedexerDatabase *> *)getDatabaseBuilder __attribute__((swift_name("getDatabaseBuilder()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Platform_iosKt")))
@interface SharedPlatform_iosKt : SharedBase
+ (NSString *)getPlatformName __attribute__((swift_name("getPlatformName()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PokedexerDatabaseKt")))
@interface SharedPokedexerDatabaseKt : SharedBase
@property (class, readonly) NSString *DATABASE_NAME __attribute__((swift_name("DATABASE_NAME")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("TextUtilsKt")))
@interface SharedTextUtilsKt : SharedBase
+ (NSString *)cleanupDescriptionTextText:(NSString *)text __attribute__((swift_name("cleanupDescriptionText(text:)")));
+ (NSString *)formatNameName:(NSString *)name __attribute__((swift_name("formatName(name:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UserDefaultsFavoritesStoreKt")))
@interface SharedUserDefaultsFavoritesStoreKt : SharedBase
+ (id<SharedFavoritesStore>)createFavoritesStore __attribute__((swift_name("createFavoritesStore()")));
@end

__attribute__((swift_name("KotlinThrowable")))
@interface SharedKotlinThrowable : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(SharedKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(SharedKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));

/**
 * @note annotations
 *   kotlin.experimental.ExperimentalNativeApi
*/
- (SharedKotlinArray<NSString *> *)getStackTrace __attribute__((swift_name("getStackTrace()")));
- (void)printStackTrace __attribute__((swift_name("printStackTrace()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) SharedKotlinThrowable * _Nullable cause __attribute__((swift_name("cause")));
@property (readonly) NSString * _Nullable message __attribute__((swift_name("message")));
- (NSError *)asError __attribute__((swift_name("asError()")));
@end

__attribute__((swift_name("KotlinException")))
@interface SharedKotlinException : SharedKotlinThrowable
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(SharedKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(SharedKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
@end

__attribute__((swift_name("OkioIOException")))
@interface SharedOkioIOException : SharedKotlinException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(SharedKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(SharedKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@end

__attribute__((swift_name("Apollo_apiCompiledSelection")))
@interface SharedApollo_apiCompiledSelection : SharedBase
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiCompiledField")))
@interface SharedApollo_apiCompiledField : SharedApollo_apiCompiledSelection
- (NSString *)nameWithArgumentsVariables:(SharedApollo_apiExecutableVariables *)variables __attribute__((swift_name("nameWithArguments(variables:)")));
- (SharedApollo_apiCompiledFieldBuilder *)doNewBuilder __attribute__((swift_name("doNewBuilder()")));
- (id _Nullable)resolveArgumentName:(NSString *)name variables:(SharedApollo_apiExecutableVariables *)variables __attribute__((swift_name("resolveArgument(name:variables:)")));
@property (readonly) NSString * _Nullable alias __attribute__((swift_name("alias")));
@property (readonly) NSArray<SharedApollo_apiCompiledArgument *> *arguments __attribute__((swift_name("arguments")));
@property (readonly) NSArray<SharedApollo_apiCompiledCondition *> *condition __attribute__((swift_name("condition")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) NSString *responseName __attribute__((swift_name("responseName")));
@property (readonly) NSArray<SharedApollo_apiCompiledSelection *> *selections __attribute__((swift_name("selections")));
@property (readonly) SharedApollo_apiCompiledType *type __attribute__((swift_name("type")));
@end

__attribute__((swift_name("OkioCloseable")))
@protocol SharedOkioCloseable
@required

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)closeAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("close_()")));
@end

__attribute__((swift_name("Apollo_apiJsonWriter")))
@protocol SharedApollo_apiJsonWriter <SharedOkioCloseable>
@required

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id<SharedApollo_apiJsonWriter> _Nullable)beginArrayAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("beginArray()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id<SharedApollo_apiJsonWriter> _Nullable)beginObjectAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("beginObject()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id<SharedApollo_apiJsonWriter> _Nullable)endArrayAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("endArray()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id<SharedApollo_apiJsonWriter> _Nullable)endObjectAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("endObject()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)flushAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("flush()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id<SharedApollo_apiJsonWriter> _Nullable)nameName:(NSString *)name error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("name(name:)")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id<SharedApollo_apiJsonWriter> _Nullable)nullValueAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("nullValue()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id<SharedApollo_apiJsonWriter> _Nullable)valueValue:(id<SharedApollo_apiUpload>)value error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("value(value:)")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id<SharedApollo_apiJsonWriter> _Nullable)valueValue:(SharedApollo_apiJsonNumber *)value error_:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("value(value_:)")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id<SharedApollo_apiJsonWriter> _Nullable)valueValue:(BOOL)value error__:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("value(value__:)")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id<SharedApollo_apiJsonWriter> _Nullable)valueValue:(double)value error___:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("value(value___:)")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id<SharedApollo_apiJsonWriter> _Nullable)valueValue:(int32_t)value error____:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("value(value____:)")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id<SharedApollo_apiJsonWriter> _Nullable)valueValue:(int64_t)value error_____:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("value(value_____:)")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id<SharedApollo_apiJsonWriter> _Nullable)valueValue:(NSString *)value error______:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("value(value______:)")));
@property (readonly) NSString *path __attribute__((swift_name("path")));
@end

__attribute__((swift_name("Apollo_apiExecutionContext")))
@protocol SharedApollo_apiExecutionContext
@required
- (id _Nullable)foldInitial:(id _Nullable)initial operation:(id _Nullable (^)(id _Nullable, id<SharedApollo_apiExecutionContextElement>))operation __attribute__((swift_name("fold(initial:operation:)")));
- (id<SharedApollo_apiExecutionContextElement> _Nullable)getKey:(id<SharedApollo_apiExecutionContextKey>)key __attribute__((swift_name("get(key:)")));
- (id<SharedApollo_apiExecutionContext>)minusKeyKey:(id<SharedApollo_apiExecutionContextKey>)key __attribute__((swift_name("minusKey(key:)")));
- (id<SharedApollo_apiExecutionContext>)plusContext:(id<SharedApollo_apiExecutionContext>)context __attribute__((swift_name("plus(context:)")));
@end

__attribute__((swift_name("Apollo_apiExecutionContextElement")))
@protocol SharedApollo_apiExecutionContextElement <SharedApollo_apiExecutionContext>
@required
@property (readonly) id<SharedApollo_apiExecutionContextKey> key __attribute__((swift_name("key")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiCustomScalarAdapters")))
@interface SharedApollo_apiCustomScalarAdapters : SharedBase <SharedApollo_apiExecutionContextElement>
@property (class, readonly, getter=companion) SharedApollo_apiCustomScalarAdaptersKey *companion __attribute__((swift_name("companion")));
- (SharedApollo_apiCustomScalarAdaptersBuilder *)doNewBuilder __attribute__((swift_name("doNewBuilder()")));
- (id<SharedApollo_apiAdapter>)responseAdapterForCustomScalar:(SharedApollo_apiCustomScalarType *)customScalar __attribute__((swift_name("responseAdapterFor(customScalar:)")));
- (NSSet<NSString *> *)variables __attribute__((swift_name("variables()"))) __attribute__((deprecated("Use adapterContext.variables() instead")));
@property (readonly) SharedApollo_apiAdapterContext *adapterContext __attribute__((swift_name("adapterContext")));
@property (readonly) id<SharedApollo_apiExecutionContextKey> key __attribute__((swift_name("key")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreFlow")))
@protocol SharedKotlinx_coroutines_coreFlow
@required

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)collectCollector:(id<SharedKotlinx_coroutines_coreFlowCollector>)collector completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("collect(collector:completionHandler:)")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreSharedFlow")))
@protocol SharedKotlinx_coroutines_coreSharedFlow <SharedKotlinx_coroutines_coreFlow>
@required
@property (readonly) NSArray<id> *replayCache __attribute__((swift_name("replayCache")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreStateFlow")))
@protocol SharedKotlinx_coroutines_coreStateFlow <SharedKotlinx_coroutines_coreSharedFlow>
@required
@property (readonly) id _Nullable value_ __attribute__((swift_name("value_")));
@end

__attribute__((swift_name("Apollo_apiJsonReader")))
@protocol SharedApollo_apiJsonReader <SharedOkioCloseable>
@required

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id<SharedApollo_apiJsonReader> _Nullable)beginArrayAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("beginArray()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id<SharedApollo_apiJsonReader> _Nullable)beginObjectAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("beginObject()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id<SharedApollo_apiJsonReader> _Nullable)endArrayAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("endArray()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (id<SharedApollo_apiJsonReader> _Nullable)endObjectAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("endObject()")));
- (NSArray<id> *)getPath __attribute__((swift_name("getPath()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)hasNextAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("hasNext()"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)nextBooleanAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("nextBoolean()"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (double)nextDoubleAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("nextDouble()"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (int32_t)nextIntAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("nextInt()"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (int64_t)nextLongAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("nextLong()"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (NSString * _Nullable)nextNameAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("nextName()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedKotlinNothing * _Nullable)nextNullAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("nextNull()"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedApollo_apiJsonNumber * _Nullable)nextNumberAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("nextNumber()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (NSString * _Nullable)nextStringAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("nextString()"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (SharedApollo_apiJsonReaderToken * _Nullable)peekAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("peek()")));
- (void)rewind __attribute__((swift_name("rewind()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (int32_t)selectNameNames:(NSArray<NSString *> *)names error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("selectName(names:)"))) __attribute__((swift_error(nonnull_error)));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)skipValueAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("skipValue()")));
@end

__attribute__((swift_name("Apollo_apiExecutionOptions")))
@protocol SharedApollo_apiExecutionOptions
@required
@property (readonly) SharedBoolean * _Nullable canBeBatched __attribute__((swift_name("canBeBatched")));
@property (readonly) SharedBoolean * _Nullable enableAutoPersistedQueries __attribute__((swift_name("enableAutoPersistedQueries")));
@property (readonly) id<SharedApollo_apiExecutionContext> executionContext __attribute__((swift_name("executionContext")));
@property (readonly) NSArray<SharedApollo_apiHttpHeader *> * _Nullable httpHeaders __attribute__((swift_name("httpHeaders")));
@property (readonly) SharedApollo_apiHttpMethod * _Nullable httpMethod __attribute__((swift_name("httpMethod")));
@property (readonly) SharedBoolean * _Nullable sendApqExtensions __attribute__((swift_name("sendApqExtensions")));
@property (readonly) SharedBoolean * _Nullable sendDocument __attribute__((swift_name("sendDocument")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_runtimeApolloClient")))
@interface SharedApollo_runtimeApolloClient : SharedBase <SharedApollo_apiExecutionOptions, SharedOkioCloseable>
@property (class, readonly, getter=companion) SharedApollo_runtimeApolloClientCompanion *companion __attribute__((swift_name("companion")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)closeAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("close_()")));
- (void)dispose __attribute__((swift_name("dispose()"))) __attribute__((deprecated("Use close() instead or call okio.use { }")));
- (id<SharedKotlinx_coroutines_coreFlow>)executeAsFlowApolloRequest:(SharedApollo_apiApolloRequest<id<SharedApollo_apiOperationData>> *)apolloRequest __attribute__((swift_name("executeAsFlow(apolloRequest:)")));
- (SharedApollo_runtimeApolloCall<id<SharedApollo_apiMutationData>> *)mutateMutation:(id<SharedApollo_apiMutation>)mutation __attribute__((swift_name("mutate(mutation:)"))) __attribute__((deprecated("Used for backward compatibility with 2.x")));
- (SharedApollo_runtimeApolloCall<id<SharedApollo_apiMutationData>> *)mutationMutation:(id<SharedApollo_apiMutation>)mutation __attribute__((swift_name("mutation(mutation:)")));
- (SharedApollo_runtimeApolloClientBuilder *)doNewBuilder __attribute__((swift_name("doNewBuilder()")));
- (void)prefetchOperation:(id<SharedApollo_apiOperation>)operation __attribute__((swift_name("prefetch(operation:)"))) __attribute__((unavailable("Use a query and ignore the result")));
- (SharedApollo_runtimeApolloCall<id<SharedApollo_apiQueryData>> *)queryQuery:(id<SharedApollo_apiQuery>)query __attribute__((swift_name("query(query:)")));
- (SharedApollo_runtimeApolloCall<id<SharedApollo_apiSubscriptionData>> *)subscribeSubscription:(id<SharedApollo_apiSubscription>)subscription __attribute__((swift_name("subscribe(subscription:)"))) __attribute__((deprecated("Used for backward compatibility with 2.x")));
- (SharedApollo_runtimeApolloCall<id<SharedApollo_apiSubscriptionData>> *)subscriptionSubscription:(id<SharedApollo_apiSubscription>)subscription __attribute__((swift_name("subscription(subscription:)")));
@property (readonly) SharedBoolean * _Nullable canBeBatched __attribute__((swift_name("canBeBatched")));
@property (readonly) SharedApollo_apiCustomScalarAdapters *customScalarAdapters __attribute__((swift_name("customScalarAdapters")));
@property (readonly) SharedBoolean * _Nullable enableAutoPersistedQueries __attribute__((swift_name("enableAutoPersistedQueries")));
@property (readonly) id<SharedApollo_apiExecutionContext> executionContext __attribute__((swift_name("executionContext")));
@property (readonly) NSArray<SharedApollo_apiHttpHeader *> * _Nullable httpHeaders __attribute__((swift_name("httpHeaders")));
@property (readonly) SharedApollo_apiHttpMethod * _Nullable httpMethod __attribute__((swift_name("httpMethod")));
@property (readonly) NSArray<id<SharedApollo_runtimeApolloInterceptor>> *interceptors __attribute__((swift_name("interceptors")));
@property (readonly) id<SharedApollo_runtimeNetworkTransport> networkTransport __attribute__((swift_name("networkTransport")));
@property (readonly) SharedBoolean * _Nullable sendApqExtensions __attribute__((swift_name("sendApqExtensions")));
@property (readonly) SharedBoolean * _Nullable sendDocument __attribute__((swift_name("sendDocument")));
@property (readonly) id<SharedApollo_runtimeNetworkTransport> subscriptionNetworkTransport __attribute__((swift_name("subscriptionNetworkTransport")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinNothing")))
@interface SharedKotlinNothing : SharedBase
@end

__attribute__((swift_name("KotlinRuntimeException")))
@interface SharedKotlinRuntimeException : SharedKotlinException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(SharedKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(SharedKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
@end

__attribute__((swift_name("KotlinIllegalStateException")))
@interface SharedKotlinIllegalStateException : SharedKotlinRuntimeException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(SharedKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(SharedKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.4")
*/
__attribute__((swift_name("KotlinCancellationException")))
@interface SharedKotlinCancellationException : SharedKotlinIllegalStateException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(SharedKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(SharedKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinArray")))
@interface SharedKotlinArray<T> : SharedBase
+ (instancetype)arrayWithSize:(int32_t)size init:(T _Nullable (^)(SharedInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (T _Nullable)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (id<SharedKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(T _Nullable)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size __attribute__((swift_name("size")));
@end

__attribute__((swift_name("KotlinKDeclarationContainer")))
@protocol SharedKotlinKDeclarationContainer
@required
@end

__attribute__((swift_name("KotlinKAnnotatedElement")))
@protocol SharedKotlinKAnnotatedElement
@required
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.1")
*/
__attribute__((swift_name("KotlinKClassifier")))
@protocol SharedKotlinKClassifier
@required
@end

__attribute__((swift_name("KotlinKClass")))
@protocol SharedKotlinKClass <SharedKotlinKDeclarationContainer, SharedKotlinKAnnotatedElement, SharedKotlinKClassifier>
@required

/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.1")
*/
- (BOOL)isInstanceValue:(id _Nullable)value __attribute__((swift_name("isInstance(value:)")));
@property (readonly) NSString * _Nullable qualifiedName __attribute__((swift_name("qualifiedName")));
@property (readonly) NSString * _Nullable simpleName __attribute__((swift_name("simpleName")));
@end

__attribute__((swift_name("Room_runtimeMigration")))
@interface SharedRoom_runtimeMigration : SharedBase
- (instancetype)initWithStartVersion:(int32_t)startVersion endVersion:(int32_t)endVersion __attribute__((swift_name("init(startVersion:endVersion:)"))) __attribute__((objc_designated_initializer));
- (void)migrateConnection:(id<SharedSqliteSQLiteConnection>)connection __attribute__((swift_name("migrate(connection:)")));
@property (readonly) int32_t endVersion __attribute__((swift_name("endVersion")));
@property (readonly) int32_t startVersion __attribute__((swift_name("startVersion")));
@end

__attribute__((swift_name("Room_runtimeAutoMigrationSpec")))
@protocol SharedRoom_runtimeAutoMigrationSpec
@required
- (void)onPostMigrateConnection:(id<SharedSqliteSQLiteConnection>)connection __attribute__((swift_name("onPostMigrate(connection:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Room_runtimeInvalidationTracker")))
@interface SharedRoom_runtimeInvalidationTracker : SharedBase

/**
 * @note annotations
 *   androidx.annotation.RestrictTo(value=[Scope.LIBRARY_GROUP_PREFIX])
*/
- (instancetype)initWithDatabase:(SharedRoom_runtimeRoomDatabase *)database shadowTablesMap:(NSDictionary<NSString *, NSString *> *)shadowTablesMap viewTables:(NSDictionary<NSString *, NSSet<NSString *> *> *)viewTables tableNames:(SharedKotlinArray<NSString *> *)tableNames __attribute__((swift_name("init(database:shadowTablesMap:viewTables:tableNames:)"))) __attribute__((objc_designated_initializer));

/**
 * @note annotations
 *   kotlin.jvm.JvmOverloads
*/
- (id<SharedKotlinx_coroutines_coreFlow>)createFlowTables:(SharedKotlinArray<NSString *> *)tables emitInitialState:(BOOL)emitInitialState __attribute__((swift_name("createFlow(tables:emitInitialState:)")));

/**
 * @note annotations
 *   androidx.annotation.RestrictTo(value=[Scope.LIBRARY_GROUP])
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)refreshTables:(SharedKotlinArray<NSString *> *)tables completionHandler:(void (^)(SharedBoolean * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("refresh(tables:completionHandler:)")));
- (void)refreshAsync __attribute__((swift_name("refreshAsync()")));
@end

__attribute__((swift_name("Room_runtimeRoomOpenDelegateMarker")))
@protocol SharedRoom_runtimeRoomOpenDelegateMarker
@required
@end

__attribute__((swift_name("Kotlinx_coroutines_coreCoroutineScope")))
@protocol SharedKotlinx_coroutines_coreCoroutineScope
@required
@property (readonly) id<SharedKotlinCoroutineContext> coroutineContext __attribute__((swift_name("coroutineContext")));
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="2.0")
*/
__attribute__((swift_name("KotlinAutoCloseable")))
@protocol SharedKotlinAutoCloseable
@required
- (void)close __attribute__((swift_name("close()")));
@end

__attribute__((swift_name("SqliteSQLiteConnection")))
@protocol SharedSqliteSQLiteConnection <SharedKotlinAutoCloseable>
@required
- (id<SharedSqliteSQLiteStatement>)prepareSql:(NSString *)sql __attribute__((swift_name("prepare(sql:)")));
@end


/**
 * @note annotations
 *   androidx.annotation.RestrictTo(value=[Scope.LIBRARY_GROUP_PREFIX])
*/
__attribute__((swift_name("Room_runtimeRoomOpenDelegate")))
@interface SharedRoom_runtimeRoomOpenDelegate : SharedBase <SharedRoom_runtimeRoomOpenDelegateMarker>
- (instancetype)initWithVersion:(int32_t)version identityHash:(NSString *)identityHash legacyIdentityHash:(NSString *)legacyIdentityHash __attribute__((swift_name("init(version:identityHash:legacyIdentityHash:)"))) __attribute__((objc_designated_initializer));
- (void)createAllTablesConnection:(id<SharedSqliteSQLiteConnection>)connection __attribute__((swift_name("createAllTables(connection:)")));
- (void)dropAllTablesConnection:(id<SharedSqliteSQLiteConnection>)connection __attribute__((swift_name("dropAllTables(connection:)")));
- (void)onCreateConnection:(id<SharedSqliteSQLiteConnection>)connection __attribute__((swift_name("onCreate(connection:)")));
- (void)onOpenConnection:(id<SharedSqliteSQLiteConnection>)connection __attribute__((swift_name("onOpen(connection:)")));
- (void)onPostMigrateConnection:(id<SharedSqliteSQLiteConnection>)connection __attribute__((swift_name("onPostMigrate(connection:)")));
- (void)onPreMigrateConnection:(id<SharedSqliteSQLiteConnection>)connection __attribute__((swift_name("onPreMigrate(connection:)")));
- (SharedRoom_runtimeRoomOpenDelegateValidationResult *)onValidateSchemaConnection:(id<SharedSqliteSQLiteConnection>)connection __attribute__((swift_name("onValidateSchema(connection:)")));
@property (readonly) NSString *identityHash __attribute__((swift_name("identityHash")));
@property (readonly) NSString *legacyIdentityHash __attribute__((swift_name("legacyIdentityHash")));
@property (readonly) int32_t version __attribute__((swift_name("version")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinEnumCompanion")))
@interface SharedKotlinEnumCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedKotlinEnumCompanion *shared __attribute__((swift_name("shared")));
@end

__attribute__((swift_name("Apollo_apiCompiledType")))
@interface SharedApollo_apiCompiledType : SharedBase
- (SharedApollo_apiCompiledNamedType *)leafType __attribute__((swift_name("leafType()"))) __attribute__((deprecated("Use rawType instead")));
- (SharedApollo_apiCompiledNamedType *)rawType __attribute__((swift_name("rawType()")));
@end

__attribute__((swift_name("Apollo_apiCompiledNamedType")))
@interface SharedApollo_apiCompiledNamedType : SharedApollo_apiCompiledType
- (SharedApollo_apiCompiledNamedType *)leafType __attribute__((swift_name("leafType()"))) __attribute__((deprecated("Use rawType instead")));
- (SharedApollo_apiCompiledNamedType *)rawType __attribute__((swift_name("rawType()")));
@property (readonly, getter=name_) NSString *name __attribute__((swift_name("name")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiCustomScalarType")))
@interface SharedApollo_apiCustomScalarType : SharedApollo_apiCompiledNamedType
- (instancetype)initWithName:(NSString *)name className:(NSString *)className __attribute__((swift_name("init(name:className:)"))) __attribute__((objc_designated_initializer));
@property (readonly) NSString *className __attribute__((swift_name("className")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiObjectType")))
@interface SharedApollo_apiObjectType : SharedApollo_apiCompiledNamedType
- (instancetype)initWithName:(NSString *)name keyFields:(NSArray<NSString *> *)keyFields implements:(NSArray<SharedApollo_apiInterfaceType *> *)implements __attribute__((swift_name("init(name:keyFields:implements:)"))) __attribute__((objc_designated_initializer)) __attribute__((deprecated("Use the Builder instead")));
- (SharedApollo_apiObjectTypeBuilder *)doNewBuilder __attribute__((swift_name("doNewBuilder()")));
@property (readonly) NSArray<NSString *> *embeddedFields __attribute__((swift_name("embeddedFields")));
@property (readonly) NSArray<SharedApollo_apiInterfaceType *> *implements __attribute__((swift_name("implements")));
@property (readonly) NSArray<NSString *> *keyFields __attribute__((swift_name("keyFields")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinUnit")))
@interface SharedKotlinUnit : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)unit __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedKotlinUnit *shared __attribute__((swift_name("shared")));
- (NSString *)description __attribute__((swift_name("description()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Room_runtimeRoomDatabaseBuilder")))
@interface SharedRoom_runtimeRoomDatabaseBuilder<T> : SharedBase
- (SharedRoom_runtimeRoomDatabaseBuilder<T> *)addAutoMigrationSpecAutoMigrationSpec:(id<SharedRoom_runtimeAutoMigrationSpec>)autoMigrationSpec __attribute__((swift_name("addAutoMigrationSpec(autoMigrationSpec:)")));
- (SharedRoom_runtimeRoomDatabaseBuilder<T> *)addCallbackCallback:(SharedRoom_runtimeRoomDatabaseCallback *)callback __attribute__((swift_name("addCallback(callback:)")));
- (SharedRoom_runtimeRoomDatabaseBuilder<T> *)addMigrationsMigrations:(SharedKotlinArray<SharedRoom_runtimeMigration *> *)migrations __attribute__((swift_name("addMigrations(migrations:)")));
- (SharedRoom_runtimeRoomDatabaseBuilder<T> *)addTypeConverterTypeConverter:(id)typeConverter __attribute__((swift_name("addTypeConverter(typeConverter:)")));
- (T)build __attribute__((swift_name("build()")));
- (SharedRoom_runtimeRoomDatabaseBuilder<T> *)fallbackToDestructiveMigrationDropAllTables:(BOOL)dropAllTables __attribute__((swift_name("fallbackToDestructiveMigration(dropAllTables:)")));
- (SharedRoom_runtimeRoomDatabaseBuilder<T> *)fallbackToDestructiveMigrationFromDropAllTables:(BOOL)dropAllTables startVersions:(SharedKotlinIntArray *)startVersions __attribute__((swift_name("fallbackToDestructiveMigrationFrom(dropAllTables:startVersions:)")));
- (SharedRoom_runtimeRoomDatabaseBuilder<T> *)fallbackToDestructiveMigrationOnDowngradeDropAllTables:(BOOL)dropAllTables __attribute__((swift_name("fallbackToDestructiveMigrationOnDowngrade(dropAllTables:)")));
- (SharedRoom_runtimeRoomDatabaseBuilder<T> *)setDriverDriver:(id<SharedSqliteSQLiteDriver>)driver __attribute__((swift_name("setDriver(driver:)")));
- (SharedRoom_runtimeRoomDatabaseBuilder<T> *)setJournalModeJournalMode:(SharedRoom_runtimeRoomDatabaseJournalMode *)journalMode __attribute__((swift_name("setJournalMode(journalMode:)")));
- (SharedRoom_runtimeRoomDatabaseBuilder<T> *)setQueryCoroutineContextContext:(id<SharedKotlinCoroutineContext>)context __attribute__((swift_name("setQueryCoroutineContext(context:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiExecutableVariables")))
@interface SharedApollo_apiExecutableVariables : SharedBase
- (instancetype)initWithValueMap:(NSDictionary<NSString *, id> *)valueMap __attribute__((swift_name("init(valueMap:)"))) __attribute__((objc_designated_initializer));
@property (readonly) NSDictionary<NSString *, id> *valueMap __attribute__((swift_name("valueMap")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiCompiledField.Builder")))
@interface SharedApollo_apiCompiledFieldBuilder : SharedBase
- (instancetype)initWithCompiledField:(SharedApollo_apiCompiledField *)compiledField __attribute__((swift_name("init(compiledField:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithName:(NSString *)name type:(SharedApollo_apiCompiledType *)type __attribute__((swift_name("init(name:type:)"))) __attribute__((objc_designated_initializer));
- (SharedApollo_apiCompiledFieldBuilder *)aliasAlias:(NSString * _Nullable)alias __attribute__((swift_name("alias(alias:)")));
- (SharedApollo_apiCompiledFieldBuilder *)argumentsArguments:(NSArray<SharedApollo_apiCompiledArgument *> *)arguments __attribute__((swift_name("arguments(arguments:)")));
- (SharedApollo_apiCompiledField *)build __attribute__((swift_name("build()")));
- (SharedApollo_apiCompiledFieldBuilder *)conditionCondition:(NSArray<SharedApollo_apiCompiledCondition *> *)condition __attribute__((swift_name("condition(condition:)")));
- (SharedApollo_apiCompiledFieldBuilder *)selectionsSelections:(NSArray<SharedApollo_apiCompiledSelection *> *)selections __attribute__((swift_name("selections(selections:)")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) SharedApollo_apiCompiledType *type __attribute__((swift_name("type")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiCompiledArgument")))
@interface SharedApollo_apiCompiledArgument : SharedBase
- (instancetype)initWithName:(NSString *)name value:(id _Nullable)value isKey:(BOOL)isKey __attribute__((swift_name("init(name:value:isKey:)"))) __attribute__((objc_designated_initializer)) __attribute__((deprecated("Use the Builder instead")));
@property (readonly) BOOL isKey __attribute__((swift_name("isKey")));

/**
 * @note annotations
 *   com.apollographql.apollo3.annotations.ApolloExperimental
*/
@property (readonly) BOOL isPagination __attribute__((swift_name("isPagination")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) id _Nullable value __attribute__((swift_name("value")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiCompiledCondition")))
@interface SharedApollo_apiCompiledCondition : SharedBase
- (instancetype)initWithName:(NSString *)name inverted:(BOOL)inverted __attribute__((swift_name("init(name:inverted:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithName:(NSString *)name inverted:(BOOL)inverted defaultValue:(BOOL)defaultValue __attribute__((swift_name("init(name:inverted:defaultValue:)"))) __attribute__((objc_designated_initializer));
- (SharedApollo_apiCompiledCondition *)doCopyName:(NSString *)name inverted:(BOOL)inverted __attribute__((swift_name("doCopy(name:inverted:)")));
- (SharedApollo_apiCompiledCondition *)doCopyName:(NSString *)name inverted:(BOOL)inverted defaultValue:(BOOL)defaultValue __attribute__((swift_name("doCopy(name:inverted:defaultValue:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) BOOL defaultValue __attribute__((swift_name("defaultValue")));
@property (readonly) BOOL inverted __attribute__((swift_name("inverted")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@end

__attribute__((swift_name("Apollo_apiUpload")))
@protocol SharedApollo_apiUpload
@required
- (void)writeToSink:(id<SharedOkioBufferedSink>)sink __attribute__((swift_name("writeTo(sink:)")));
@property (readonly) int64_t contentLength __attribute__((swift_name("contentLength")));
@property (readonly) NSString *contentType __attribute__((swift_name("contentType")));
@property (readonly) NSString * _Nullable fileName __attribute__((swift_name("fileName")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiJsonNumber")))
@interface SharedApollo_apiJsonNumber : SharedBase
- (instancetype)initWithValue:(NSString *)value __attribute__((swift_name("init(value:)"))) __attribute__((objc_designated_initializer));
@property (readonly) NSString *value __attribute__((swift_name("value")));
@end

__attribute__((swift_name("Apollo_apiExecutionContextKey")))
@protocol SharedApollo_apiExecutionContextKey
@required
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiCustomScalarAdapters.Key")))
@interface SharedApollo_apiCustomScalarAdaptersKey : SharedBase <SharedApollo_apiExecutionContextKey>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)key __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedApollo_apiCustomScalarAdaptersKey *shared __attribute__((swift_name("shared")));
@property (readonly) SharedApollo_apiCustomScalarAdapters *Empty __attribute__((swift_name("Empty")));

/**
 * @note annotations
 *   com.apollographql.apollo3.annotations.ApolloExperimental
*/
@property (readonly) SharedApollo_apiCustomScalarAdapters *PassThrough __attribute__((swift_name("PassThrough")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiCustomScalarAdapters.Builder")))
@interface SharedApollo_apiCustomScalarAdaptersBuilder : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (SharedApollo_apiCustomScalarAdaptersBuilder *)adapterContextAdapterContext:(SharedApollo_apiAdapterContext *)adapterContext __attribute__((swift_name("adapterContext(adapterContext:)")));
- (SharedApollo_apiCustomScalarAdaptersBuilder *)addCustomScalarType:(SharedApollo_apiCustomScalarType *)customScalarType customScalarAdapter:(id<SharedApollo_apiAdapter>)customScalarAdapter __attribute__((swift_name("add(customScalarType:customScalarAdapter:)")));
- (SharedApollo_apiCustomScalarAdaptersBuilder *)addCustomScalarType:(SharedApollo_apiCustomScalarType *)customScalarType customTypeAdapter:(id<SharedApollo_apiCustomTypeAdapter>)customTypeAdapter __attribute__((swift_name("add(customScalarType:customTypeAdapter:)"))) __attribute__((deprecated("Used for backward compatibility with 2.x")));
- (SharedApollo_apiCustomScalarAdaptersBuilder *)addAllCustomScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters __attribute__((swift_name("addAll(customScalarAdapters:)")));
- (SharedApollo_apiCustomScalarAdapters *)build __attribute__((swift_name("build()")));
- (void)clear __attribute__((swift_name("clear()")));

/**
 * @note annotations
 *   com.apollographql.apollo3.annotations.ApolloExperimental
*/
- (SharedApollo_apiCustomScalarAdaptersBuilder *)unsafeUnsafe:(BOOL)unsafe __attribute__((swift_name("unsafe(unsafe:)")));
- (SharedApollo_apiCustomScalarAdaptersBuilder *)variablesVariables:(SharedApollo_apiExecutableVariables *)variables __attribute__((swift_name("variables(variables:)"))) __attribute__((deprecated("Use AdapterContext.Builder.variables() instead")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiAdapterContext")))
@interface SharedApollo_apiAdapterContext : SharedBase
- (BOOL)hasDeferredFragmentPath:(NSArray<id> *)path label:(NSString * _Nullable)label __attribute__((swift_name("hasDeferredFragment(path:label:)")));
- (SharedApollo_apiAdapterContextBuilder *)doNewBuilder __attribute__((swift_name("doNewBuilder()")));
- (NSSet<NSString *> *)variables __attribute__((swift_name("variables()")));
@property (readonly) BOOL serializeVariablesWithDefaultBooleanValues __attribute__((swift_name("serializeVariablesWithDefaultBooleanValues")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreFlowCollector")))
@protocol SharedKotlinx_coroutines_coreFlowCollector
@required

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)emitValue:(id _Nullable)value completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("emit(value:completionHandler:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiJsonReaderToken")))
@interface SharedApollo_apiJsonReaderToken : SharedKotlinEnum<SharedApollo_apiJsonReaderToken *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) SharedApollo_apiJsonReaderToken *beginArray __attribute__((swift_name("beginArray")));
@property (class, readonly) SharedApollo_apiJsonReaderToken *endArray __attribute__((swift_name("endArray")));
@property (class, readonly) SharedApollo_apiJsonReaderToken *beginObject __attribute__((swift_name("beginObject")));
@property (class, readonly) SharedApollo_apiJsonReaderToken *endObject __attribute__((swift_name("endObject")));
@property (class, readonly) SharedApollo_apiJsonReaderToken *name __attribute__((swift_name("name")));
@property (class, readonly) SharedApollo_apiJsonReaderToken *string __attribute__((swift_name("string")));
@property (class, readonly) SharedApollo_apiJsonReaderToken *number __attribute__((swift_name("number")));
@property (class, readonly) SharedApollo_apiJsonReaderToken *long_ __attribute__((swift_name("long_")));
@property (class, readonly) SharedApollo_apiJsonReaderToken *boolean __attribute__((swift_name("boolean")));
@property (class, readonly) SharedApollo_apiJsonReaderToken *null __attribute__((swift_name("null")));
@property (class, readonly) SharedApollo_apiJsonReaderToken *endDocument __attribute__((swift_name("endDocument")));
@property (class, readonly) SharedApollo_apiJsonReaderToken *any __attribute__((swift_name("any")));
+ (SharedKotlinArray<SharedApollo_apiJsonReaderToken *> *)values __attribute__((swift_name("values()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiHttpHeader")))
@interface SharedApollo_apiHttpHeader : SharedBase
- (instancetype)initWithName:(NSString *)name value:(NSString *)value __attribute__((swift_name("init(name:value:)"))) __attribute__((objc_designated_initializer));
- (SharedApollo_apiHttpHeader *)doCopyName:(NSString *)name value:(NSString *)value __attribute__((swift_name("doCopy(name:value:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) NSString *value __attribute__((swift_name("value")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiHttpMethod")))
@interface SharedApollo_apiHttpMethod : SharedKotlinEnum<SharedApollo_apiHttpMethod *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) SharedApollo_apiHttpMethod *get __attribute__((swift_name("get")));
@property (class, readonly) SharedApollo_apiHttpMethod *post __attribute__((swift_name("post")));
+ (SharedKotlinArray<SharedApollo_apiHttpMethod *> *)values __attribute__((swift_name("values()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_runtimeApolloClient.Companion")))
@interface SharedApollo_runtimeApolloClientCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedApollo_runtimeApolloClientCompanion *shared __attribute__((swift_name("shared")));

/**
 * @note annotations
 *   kotlin.jvm.JvmStatic
*/
- (SharedApollo_runtimeApolloClientBuilder *)builder __attribute__((swift_name("builder()"))) __attribute__((deprecated("Used for backward compatibility with 2.x")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiApolloRequest")))
@interface SharedApollo_apiApolloRequest<D> : SharedBase <SharedApollo_apiExecutionOptions>
- (SharedApollo_apiApolloRequestBuilder<D> *)doNewBuilder __attribute__((swift_name("doNewBuilder()")));

/**
 * @note annotations
 *   com.apollographql.apollo3.annotations.ApolloExperimental
*/
- (SharedApollo_apiApolloRequestBuilder<id<SharedApollo_apiOperationData>> *)doNewBuilderOperation:(id<SharedApollo_apiOperation>)operation __attribute__((swift_name("doNewBuilder(operation:)")));
@property (readonly) SharedBoolean * _Nullable canBeBatched __attribute__((swift_name("canBeBatched")));
@property (readonly) SharedBoolean * _Nullable enableAutoPersistedQueries __attribute__((swift_name("enableAutoPersistedQueries")));
@property (readonly) id<SharedApollo_apiExecutionContext> executionContext __attribute__((swift_name("executionContext")));
@property (readonly) NSArray<SharedApollo_apiHttpHeader *> * _Nullable httpHeaders __attribute__((swift_name("httpHeaders")));
@property (readonly) SharedApollo_apiHttpMethod * _Nullable httpMethod __attribute__((swift_name("httpMethod")));
@property (readonly) id<SharedApollo_apiOperation> operation __attribute__((swift_name("operation")));
@property (readonly) SharedUuidUuid *requestUuid __attribute__((swift_name("requestUuid")));
@property (readonly) SharedBoolean * _Nullable sendApqExtensions __attribute__((swift_name("sendApqExtensions")));
@property (readonly) SharedBoolean * _Nullable sendDocument __attribute__((swift_name("sendDocument")));
@end

__attribute__((swift_name("Apollo_apiMutationData")))
@protocol SharedApollo_apiMutationData <SharedApollo_apiOperationData>
@required
@end

__attribute__((swift_name("Apollo_apiMutableExecutionOptions")))
@protocol SharedApollo_apiMutableExecutionOptions <SharedApollo_apiExecutionOptions>
@required
- (id _Nullable)addExecutionContextExecutionContext:(id<SharedApollo_apiExecutionContext>)executionContext __attribute__((swift_name("addExecutionContext(executionContext:)")));
- (id _Nullable)addHttpHeaderName:(NSString *)name value:(NSString *)value __attribute__((swift_name("addHttpHeader(name:value:)")));
- (id _Nullable)canBeBatchedCanBeBatched:(SharedBoolean * _Nullable)canBeBatched __attribute__((swift_name("canBeBatched(canBeBatched:)")));
- (id _Nullable)enableAutoPersistedQueriesEnableAutoPersistedQueries:(SharedBoolean * _Nullable)enableAutoPersistedQueries __attribute__((swift_name("enableAutoPersistedQueries(enableAutoPersistedQueries:)")));
- (id _Nullable)httpHeadersHttpHeaders:(NSArray<SharedApollo_apiHttpHeader *> * _Nullable)httpHeaders __attribute__((swift_name("httpHeaders(httpHeaders:)")));
- (id _Nullable)httpMethodHttpMethod:(SharedApollo_apiHttpMethod * _Nullable)httpMethod __attribute__((swift_name("httpMethod(httpMethod:)")));
- (id _Nullable)sendApqExtensionsSendApqExtensions:(SharedBoolean * _Nullable)sendApqExtensions __attribute__((swift_name("sendApqExtensions(sendApqExtensions:)")));
- (id _Nullable)sendDocumentSendDocument:(SharedBoolean * _Nullable)sendDocument __attribute__((swift_name("sendDocument(sendDocument:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_runtimeApolloCall")))
@interface SharedApollo_runtimeApolloCall<D> : SharedBase <SharedApollo_apiMutableExecutionOptions>
- (SharedApollo_runtimeApolloCall<D> *)addExecutionContextExecutionContext:(id<SharedApollo_apiExecutionContext>)executionContext __attribute__((swift_name("addExecutionContext(executionContext:)")));
- (SharedApollo_runtimeApolloCall<D> *)addHttpHeaderName:(NSString *)name value:(NSString *)value __attribute__((swift_name("addHttpHeader(name:value:)")));
- (SharedApollo_runtimeApolloCall<D> *)canBeBatchedCanBeBatched:(SharedBoolean * _Nullable)canBeBatched __attribute__((swift_name("canBeBatched(canBeBatched:)")));
- (SharedApollo_runtimeApolloCall<D> *)doCopy __attribute__((swift_name("doCopy()")));
- (SharedApollo_runtimeApolloCall<D> *)enableAutoPersistedQueriesEnableAutoPersistedQueries:(SharedBoolean * _Nullable)enableAutoPersistedQueries __attribute__((swift_name("enableAutoPersistedQueries(enableAutoPersistedQueries:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)executeWithCompletionHandler:(void (^)(SharedApollo_apiApolloResponse<D> * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("execute(completionHandler:)")));
- (SharedApollo_runtimeApolloCall<D> *)httpHeadersHttpHeaders:(NSArray<SharedApollo_apiHttpHeader *> * _Nullable)httpHeaders __attribute__((swift_name("httpHeaders(httpHeaders:)")));
- (SharedApollo_runtimeApolloCall<D> *)httpMethodHttpMethod:(SharedApollo_apiHttpMethod * _Nullable)httpMethod __attribute__((swift_name("httpMethod(httpMethod:)")));
- (SharedApollo_runtimeApolloCall<D> *)sendApqExtensionsSendApqExtensions:(SharedBoolean * _Nullable)sendApqExtensions __attribute__((swift_name("sendApqExtensions(sendApqExtensions:)")));
- (SharedApollo_runtimeApolloCall<D> *)sendDocumentSendDocument:(SharedBoolean * _Nullable)sendDocument __attribute__((swift_name("sendDocument(sendDocument:)")));
- (id<SharedKotlinx_coroutines_coreFlow>)toFlow __attribute__((swift_name("toFlow()")));
@property SharedBoolean * _Nullable canBeBatched __attribute__((swift_name("canBeBatched")));
@property SharedBoolean * _Nullable enableAutoPersistedQueries __attribute__((swift_name("enableAutoPersistedQueries")));
@property id<SharedApollo_apiExecutionContext> executionContext __attribute__((swift_name("executionContext")));
@property NSArray<SharedApollo_apiHttpHeader *> * _Nullable httpHeaders __attribute__((swift_name("httpHeaders")));
@property SharedApollo_apiHttpMethod * _Nullable httpMethod __attribute__((swift_name("httpMethod")));
@property (readonly) id<SharedApollo_apiOperation> operation __attribute__((swift_name("operation")));
@property SharedBoolean * _Nullable sendApqExtensions __attribute__((swift_name("sendApqExtensions")));
@property SharedBoolean * _Nullable sendDocument __attribute__((swift_name("sendDocument")));
@end

__attribute__((swift_name("Apollo_apiMutation")))
@protocol SharedApollo_apiMutation <SharedApollo_apiOperation>
@required
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_runtimeApolloClient.Builder")))
@interface SharedApollo_runtimeApolloClientBuilder : SharedBase <SharedApollo_apiMutableExecutionOptions>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (SharedApollo_runtimeApolloClientBuilder *)addCustomScalarAdapterCustomScalarType:(SharedApollo_apiCustomScalarType *)customScalarType customScalarAdapter:(id<SharedApollo_apiAdapter>)customScalarAdapter __attribute__((swift_name("addCustomScalarAdapter(customScalarType:customScalarAdapter:)")));
- (SharedApollo_runtimeApolloClientBuilder *)addCustomTypeAdapterCustomScalarType:(SharedApollo_apiCustomScalarType *)customScalarType customTypeAdapter:(id<SharedApollo_apiCustomTypeAdapter>)customTypeAdapter __attribute__((swift_name("addCustomTypeAdapter(customScalarType:customTypeAdapter:)"))) __attribute__((deprecated("Used for backward compatibility with 2.x")));
- (SharedApollo_runtimeApolloClientBuilder *)addExecutionContextExecutionContext:(id<SharedApollo_apiExecutionContext>)executionContext __attribute__((swift_name("addExecutionContext(executionContext:)")));
- (SharedApollo_runtimeApolloClientBuilder *)addHttpHeaderName:(NSString *)name value:(NSString *)value __attribute__((swift_name("addHttpHeader(name:value:)")));
- (SharedApollo_runtimeApolloClientBuilder *)addHttpInterceptorHttpInterceptor:(id<SharedApollo_runtimeHttpInterceptor>)httpInterceptor __attribute__((swift_name("addHttpInterceptor(httpInterceptor:)")));
- (SharedApollo_runtimeApolloClientBuilder *)addInterceptorInterceptor:(id<SharedApollo_runtimeApolloInterceptor>)interceptor __attribute__((swift_name("addInterceptor(interceptor:)")));
- (SharedApollo_runtimeApolloClientBuilder *)addInterceptorsInterceptors:(NSArray<id<SharedApollo_runtimeApolloInterceptor>> *)interceptors __attribute__((swift_name("addInterceptors(interceptors:)")));

/**
 * @note annotations
 *   kotlin.jvm.JvmOverloads
*/
- (SharedApollo_runtimeApolloClientBuilder *)autoPersistedQueriesHttpMethodForHashedQueries:(SharedApollo_apiHttpMethod *)httpMethodForHashedQueries httpMethodForDocumentQueries:(SharedApollo_apiHttpMethod *)httpMethodForDocumentQueries enableByDefault:(BOOL)enableByDefault __attribute__((swift_name("autoPersistedQueries(httpMethodForHashedQueries:httpMethodForDocumentQueries:enableByDefault:)")));
- (SharedApollo_runtimeApolloClient *)build __attribute__((swift_name("build()")));
- (SharedApollo_runtimeApolloClientBuilder *)canBeBatchedCanBeBatched:(SharedBoolean * _Nullable)canBeBatched __attribute__((swift_name("canBeBatched(canBeBatched:)")));
- (SharedApollo_runtimeApolloClientBuilder *)doCopy __attribute__((swift_name("doCopy()")));
- (SharedApollo_runtimeApolloClientBuilder *)customScalarAdaptersCustomScalarAdapters:(SharedApollo_apiCustomScalarAdapters *)customScalarAdapters __attribute__((swift_name("customScalarAdapters(customScalarAdapters:)")));
- (SharedApollo_runtimeApolloClientBuilder *)dispatcherDispatcher:(SharedKotlinx_coroutines_coreCoroutineDispatcher * _Nullable)dispatcher __attribute__((swift_name("dispatcher(dispatcher:)")));
- (SharedApollo_runtimeApolloClientBuilder *)enableAutoPersistedQueriesEnableAutoPersistedQueries:(SharedBoolean * _Nullable)enableAutoPersistedQueries __attribute__((swift_name("enableAutoPersistedQueries(enableAutoPersistedQueries:)")));
- (SharedApollo_runtimeApolloClientBuilder *)executionContextExecutionContext:(id<SharedApollo_apiExecutionContext>)executionContext __attribute__((swift_name("executionContext(executionContext:)")));

/**
 * @note annotations
 *   kotlin.jvm.JvmOverloads
*/
- (SharedApollo_runtimeApolloClientBuilder *)httpBatchingBatchIntervalMillis:(int64_t)batchIntervalMillis maxBatchSize:(int32_t)maxBatchSize enableByDefault:(BOOL)enableByDefault __attribute__((swift_name("httpBatching(batchIntervalMillis:maxBatchSize:enableByDefault:)")));
- (SharedApollo_runtimeApolloClientBuilder *)httpEngineHttpEngine:(id<SharedApollo_runtimeHttpEngine>)httpEngine __attribute__((swift_name("httpEngine(httpEngine:)")));
- (SharedApollo_runtimeApolloClientBuilder *)httpExposeErrorBodyHttpExposeErrorBody:(BOOL)httpExposeErrorBody __attribute__((swift_name("httpExposeErrorBody(httpExposeErrorBody:)")));
- (SharedApollo_runtimeApolloClientBuilder *)httpHeadersHttpHeaders:(NSArray<SharedApollo_apiHttpHeader *> * _Nullable)httpHeaders __attribute__((swift_name("httpHeaders(httpHeaders:)")));
- (SharedApollo_runtimeApolloClientBuilder *)httpMethodHttpMethod:(SharedApollo_apiHttpMethod * _Nullable)httpMethod __attribute__((swift_name("httpMethod(httpMethod:)")));
- (SharedApollo_runtimeApolloClientBuilder *)httpServerUrlHttpServerUrl:(NSString *)httpServerUrl __attribute__((swift_name("httpServerUrl(httpServerUrl:)")));
- (SharedApollo_runtimeApolloClientBuilder *)interceptorsInterceptors:(NSArray<id<SharedApollo_runtimeApolloInterceptor>> *)interceptors __attribute__((swift_name("interceptors(interceptors:)")));
- (SharedApollo_runtimeApolloClientBuilder *)networkTransportNetworkTransport:(id<SharedApollo_runtimeNetworkTransport>)networkTransport __attribute__((swift_name("networkTransport(networkTransport:)")));
- (SharedApollo_runtimeApolloClientBuilder *)requestedDispatcherRequestedDispatcher:(SharedKotlinx_coroutines_coreCoroutineDispatcher * _Nullable)requestedDispatcher __attribute__((swift_name("requestedDispatcher(requestedDispatcher:)"))) __attribute__((deprecated("Use dispatcher instead")));
- (SharedApollo_runtimeApolloClientBuilder *)sendApqExtensionsSendApqExtensions:(SharedBoolean * _Nullable)sendApqExtensions __attribute__((swift_name("sendApqExtensions(sendApqExtensions:)")));
- (SharedApollo_runtimeApolloClientBuilder *)sendDocumentSendDocument:(SharedBoolean * _Nullable)sendDocument __attribute__((swift_name("sendDocument(sendDocument:)")));
- (SharedApollo_runtimeApolloClientBuilder *)serverUrlServerUrl:(NSString *)serverUrl __attribute__((swift_name("serverUrl(serverUrl:)")));
- (SharedApollo_runtimeApolloClientBuilder *)subscriptionNetworkTransportSubscriptionNetworkTransport:(id<SharedApollo_runtimeNetworkTransport>)subscriptionNetworkTransport __attribute__((swift_name("subscriptionNetworkTransport(subscriptionNetworkTransport:)")));
- (SharedApollo_runtimeApolloClientBuilder *)useHttpGetMethodForPersistedQueriesUseHttpGetMethodForQueries:(BOOL)useHttpGetMethodForQueries __attribute__((swift_name("useHttpGetMethodForPersistedQueries(useHttpGetMethodForQueries:)"))) __attribute__((deprecated("Used for backward compatibility with 2.x. This method throws immediately")));
- (SharedApollo_runtimeApolloClientBuilder *)useHttpGetMethodForQueriesUseHttpGetMethodForQueries:(BOOL)useHttpGetMethodForQueries __attribute__((swift_name("useHttpGetMethodForQueries(useHttpGetMethodForQueries:)"))) __attribute__((deprecated("Used for backward compatibility with 2.x")));
- (SharedApollo_runtimeApolloClientBuilder *)webSocketEngineWebSocketEngine:(id<SharedApollo_runtimeWebSocketEngine>)webSocketEngine __attribute__((swift_name("webSocketEngine(webSocketEngine:)")));
- (SharedApollo_runtimeApolloClientBuilder *)webSocketIdleTimeoutMillisWebSocketIdleTimeoutMillis:(int64_t)webSocketIdleTimeoutMillis __attribute__((swift_name("webSocketIdleTimeoutMillis(webSocketIdleTimeoutMillis:)")));
- (SharedApollo_runtimeApolloClientBuilder *)webSocketReconnectWhenReconnectWhen:(SharedBoolean *(^ _Nullable)(SharedKotlinThrowable *))reconnectWhen __attribute__((swift_name("webSocketReconnectWhen(reconnectWhen:)"))) __attribute__((deprecated("Use webSocketReopenWhen(webSocketReopenWhen: (suspend (Throwable, attempt: Long) -> Boolean))")));
- (SharedApollo_runtimeApolloClientBuilder *)webSocketReopenWhenWebSocketReopenWhen:(id<SharedKotlinSuspendFunction2>)webSocketReopenWhen __attribute__((swift_name("webSocketReopenWhen(webSocketReopenWhen:)")));
- (SharedApollo_runtimeApolloClientBuilder *)webSocketServerUrlWebSocketServerUrl:(NSString *)webSocketServerUrl __attribute__((swift_name("webSocketServerUrl(webSocketServerUrl:)")));
- (SharedApollo_runtimeApolloClientBuilder *)webSocketServerUrlWebSocketServerUrl_:(id<SharedKotlinSuspendFunction0>)webSocketServerUrl __attribute__((swift_name("webSocketServerUrl(webSocketServerUrl_:)")));
- (SharedApollo_runtimeApolloClientBuilder *)wsProtocolWsProtocolFactory:(id<SharedApollo_runtimeWsProtocolFactory>)wsProtocolFactory __attribute__((swift_name("wsProtocol(wsProtocolFactory:)")));
@property SharedBoolean * _Nullable canBeBatched __attribute__((swift_name("canBeBatched")));
@property SharedBoolean * _Nullable enableAutoPersistedQueries __attribute__((swift_name("enableAutoPersistedQueries")));
@property id<SharedApollo_apiExecutionContext> executionContext __attribute__((swift_name("executionContext")));
@property NSArray<SharedApollo_apiHttpHeader *> * _Nullable httpHeaders __attribute__((swift_name("httpHeaders")));
@property SharedApollo_apiHttpMethod * _Nullable httpMethod __attribute__((swift_name("httpMethod")));
@property (readonly) NSArray<id<SharedApollo_runtimeApolloInterceptor>> *interceptors __attribute__((swift_name("interceptors")));
@property SharedBoolean * _Nullable sendApqExtensions __attribute__((swift_name("sendApqExtensions")));
@property SharedBoolean * _Nullable sendDocument __attribute__((swift_name("sendDocument")));
@end

__attribute__((swift_name("Apollo_apiSubscriptionData")))
@protocol SharedApollo_apiSubscriptionData <SharedApollo_apiOperationData>
@required
@end

__attribute__((swift_name("Apollo_apiSubscription")))
@protocol SharedApollo_apiSubscription <SharedApollo_apiOperation>
@required
@end

__attribute__((swift_name("Apollo_runtimeApolloInterceptor")))
@protocol SharedApollo_runtimeApolloInterceptor
@required
- (id<SharedKotlinx_coroutines_coreFlow>)interceptRequest:(SharedApollo_apiApolloRequest<id<SharedApollo_apiOperationData>> *)request chain:(id<SharedApollo_runtimeApolloInterceptorChain>)chain __attribute__((swift_name("intercept(request:chain:)")));
@end

__attribute__((swift_name("Apollo_runtimeNetworkTransport")))
@protocol SharedApollo_runtimeNetworkTransport
@required
- (void)dispose __attribute__((swift_name("dispose()")));
- (id<SharedKotlinx_coroutines_coreFlow>)executeRequest:(SharedApollo_apiApolloRequest<id<SharedApollo_apiOperationData>> *)request __attribute__((swift_name("execute(request:)")));
@end

__attribute__((swift_name("KotlinIterator")))
@protocol SharedKotlinIterator
@required
- (BOOL)hasNext __attribute__((swift_name("hasNext_()")));
- (id _Nullable)next __attribute__((swift_name("next()")));
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.3")
*/
__attribute__((swift_name("KotlinCoroutineContext")))
@protocol SharedKotlinCoroutineContext
@required
- (id _Nullable)foldInitial:(id _Nullable)initial operation_:(id _Nullable (^)(id _Nullable, id<SharedKotlinCoroutineContextElement>))operation __attribute__((swift_name("fold(initial:operation_:)")));
- (id<SharedKotlinCoroutineContextElement> _Nullable)getKey_:(id<SharedKotlinCoroutineContextKey>)key __attribute__((swift_name("get(key_:)")));
- (id<SharedKotlinCoroutineContext>)minusKeyKey_:(id<SharedKotlinCoroutineContextKey>)key __attribute__((swift_name("minusKey(key_:)")));
- (id<SharedKotlinCoroutineContext>)plusContext_:(id<SharedKotlinCoroutineContext>)context __attribute__((swift_name("plus(context_:)")));
@end

__attribute__((swift_name("SqliteSQLiteStatement")))
@protocol SharedSqliteSQLiteStatement <SharedKotlinAutoCloseable>
@required

/**
 * @param index annotations androidx.annotation.IntRange(from=1.toLong())
*/
- (void)bindBlobIndex:(int32_t)index value:(SharedKotlinByteArray *)value __attribute__((swift_name("bindBlob(index:value:)")));

/**
 * @param index annotations androidx.annotation.IntRange(from=1.toLong())
*/
- (void)bindBooleanIndex:(int32_t)index value:(BOOL)value __attribute__((swift_name("bindBoolean(index:value:)")));

/**
 * @param index annotations androidx.annotation.IntRange(from=1.toLong())
*/
- (void)bindDoubleIndex:(int32_t)index value:(double)value __attribute__((swift_name("bindDouble(index:value:)")));

/**
 * @param index annotations androidx.annotation.IntRange(from=1.toLong())
*/
- (void)bindFloatIndex:(int32_t)index value:(float)value __attribute__((swift_name("bindFloat(index:value:)")));

/**
 * @param index annotations androidx.annotation.IntRange(from=1.toLong())
*/
- (void)bindIntIndex:(int32_t)index value:(int32_t)value __attribute__((swift_name("bindInt(index:value:)")));

/**
 * @param index annotations androidx.annotation.IntRange(from=1.toLong())
*/
- (void)bindLongIndex:(int32_t)index value:(int64_t)value __attribute__((swift_name("bindLong(index:value:)")));

/**
 * @param index annotations androidx.annotation.IntRange(from=1.toLong())
*/
- (void)bindNullIndex:(int32_t)index __attribute__((swift_name("bindNull(index:)")));

/**
 * @param index annotations androidx.annotation.IntRange(from=1.toLong())
*/
- (void)bindTextIndex:(int32_t)index value:(NSString *)value __attribute__((swift_name("bindText(index:value:)")));
- (void)clearBindings __attribute__((swift_name("clearBindings()")));

/**
 * @param index annotations androidx.annotation.IntRange(from=0.toLong())
*/
- (SharedKotlinByteArray *)getBlobIndex:(int32_t)index __attribute__((swift_name("getBlob(index:)")));

/**
 * @param index annotations androidx.annotation.IntRange(from=0.toLong())
*/
- (BOOL)getBooleanIndex:(int32_t)index __attribute__((swift_name("getBoolean(index:)")));
- (int32_t)getColumnCount __attribute__((swift_name("getColumnCount()")));

/**
 * @param index annotations androidx.annotation.IntRange(from=0.toLong())
*/
- (NSString *)getColumnNameIndex:(int32_t)index __attribute__((swift_name("getColumnName(index:)")));
- (NSArray<NSString *> *)getColumnNames __attribute__((swift_name("getColumnNames()")));

/**
 * @param index annotations androidx.annotation.IntRange(from=0.toLong())
*/
- (int32_t)getColumnTypeIndex:(int32_t)index __attribute__((swift_name("getColumnType(index:)")));

/**
 * @param index annotations androidx.annotation.IntRange(from=0.toLong())
*/
- (double)getDoubleIndex:(int32_t)index __attribute__((swift_name("getDouble(index:)")));

/**
 * @param index annotations androidx.annotation.IntRange(from=0.toLong())
*/
- (float)getFloatIndex:(int32_t)index __attribute__((swift_name("getFloat(index:)")));

/**
 * @param index annotations androidx.annotation.IntRange(from=0.toLong())
*/
- (int32_t)getIntIndex:(int32_t)index __attribute__((swift_name("getInt(index:)")));

/**
 * @param index annotations androidx.annotation.IntRange(from=0.toLong())
*/
- (int64_t)getLongIndex:(int32_t)index __attribute__((swift_name("getLong(index:)")));

/**
 * @param index annotations androidx.annotation.IntRange(from=0.toLong())
*/
- (NSString *)getTextIndex:(int32_t)index __attribute__((swift_name("getText(index:)")));

/**
 * @param index annotations androidx.annotation.IntRange(from=0.toLong())
*/
- (BOOL)isNullIndex:(int32_t)index __attribute__((swift_name("isNull(index:)")));
- (void)reset __attribute__((swift_name("reset()")));
- (BOOL)step __attribute__((swift_name("step()")));
@end


/**
 * @note annotations
 *   androidx.annotation.RestrictTo(value=[Scope.LIBRARY_GROUP_PREFIX])
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Room_runtimeRoomOpenDelegate.ValidationResult")))
@interface SharedRoom_runtimeRoomOpenDelegateValidationResult : SharedBase
- (instancetype)initWithIsValid:(BOOL)isValid expectedFoundMsg:(NSString * _Nullable)expectedFoundMsg __attribute__((swift_name("init(isValid:expectedFoundMsg:)"))) __attribute__((objc_designated_initializer));
@property (readonly) NSString * _Nullable expectedFoundMsg __attribute__((swift_name("expectedFoundMsg")));
@property (readonly) BOOL isValid __attribute__((swift_name("isValid")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiInterfaceType")))
@interface SharedApollo_apiInterfaceType : SharedApollo_apiCompiledNamedType
- (instancetype)initWithName:(NSString *)name keyFields:(NSArray<NSString *> *)keyFields implements:(NSArray<SharedApollo_apiInterfaceType *> *)implements __attribute__((swift_name("init(name:keyFields:implements:)"))) __attribute__((objc_designated_initializer)) __attribute__((deprecated("Use the Builder instead")));
- (SharedApollo_apiInterfaceTypeBuilder *)doNewBuilder __attribute__((swift_name("doNewBuilder()")));
@property (readonly) NSArray<NSString *> *embeddedFields __attribute__((swift_name("embeddedFields")));
@property (readonly) NSArray<SharedApollo_apiInterfaceType *> *implements __attribute__((swift_name("implements")));
@property (readonly) NSArray<NSString *> *keyFields __attribute__((swift_name("keyFields")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiObjectType.Builder")))
@interface SharedApollo_apiObjectTypeBuilder : SharedBase
- (instancetype)initWithObjectType:(SharedApollo_apiObjectType *)objectType __attribute__((swift_name("init(objectType:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithName:(NSString *)name __attribute__((swift_name("init(name:)"))) __attribute__((objc_designated_initializer));
- (SharedApollo_apiObjectType *)build __attribute__((swift_name("build()")));
- (SharedApollo_apiObjectTypeBuilder *)embeddedFieldsEmbeddedFields:(NSArray<NSString *> *)embeddedFields __attribute__((swift_name("embeddedFields(embeddedFields:)")));
- (SharedApollo_apiObjectTypeBuilder *)interfacesImplements:(NSArray<SharedApollo_apiInterfaceType *> *)implements __attribute__((swift_name("interfaces(implements:)")));
- (SharedApollo_apiObjectTypeBuilder *)keyFieldsKeyFields:(NSArray<NSString *> *)keyFields __attribute__((swift_name("keyFields(keyFields:)")));
@end

__attribute__((swift_name("Room_runtimeRoomDatabase.Callback")))
@interface SharedRoom_runtimeRoomDatabaseCallback : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)onCreateConnection:(id<SharedSqliteSQLiteConnection>)connection __attribute__((swift_name("onCreate(connection:)")));
- (void)onDestructiveMigrationConnection:(id<SharedSqliteSQLiteConnection>)connection __attribute__((swift_name("onDestructiveMigration(connection:)")));
- (void)onOpenConnection:(id<SharedSqliteSQLiteConnection>)connection __attribute__((swift_name("onOpen(connection:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinIntArray")))
@interface SharedKotlinIntArray : SharedBase
+ (instancetype)arrayWithSize:(int32_t)size __attribute__((swift_name("init(size:)")));
+ (instancetype)arrayWithSize:(int32_t)size init:(SharedInt *(^)(SharedInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (int32_t)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (SharedKotlinIntIterator *)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(int32_t)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size __attribute__((swift_name("size")));
@end

__attribute__((swift_name("SqliteSQLiteDriver")))
@protocol SharedSqliteSQLiteDriver
@required
- (id<SharedSqliteSQLiteConnection>)openFileName:(NSString *)fileName __attribute__((swift_name("open(fileName:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Room_runtimeRoomDatabase.JournalMode")))
@interface SharedRoom_runtimeRoomDatabaseJournalMode : SharedKotlinEnum<SharedRoom_runtimeRoomDatabaseJournalMode *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) SharedRoom_runtimeRoomDatabaseJournalMode *truncate __attribute__((swift_name("truncate")));
@property (class, readonly) SharedRoom_runtimeRoomDatabaseJournalMode *writeAheadLogging __attribute__((swift_name("writeAheadLogging")));
+ (SharedKotlinArray<SharedRoom_runtimeRoomDatabaseJournalMode *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<SharedRoom_runtimeRoomDatabaseJournalMode *> *entries __attribute__((swift_name("entries")));
@end

__attribute__((swift_name("OkioSink")))
@protocol SharedOkioSink <SharedOkioCloseable>
@required

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)flushAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("flush()")));
- (SharedOkioTimeout *)timeout __attribute__((swift_name("timeout()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)writeSource:(SharedOkioBuffer *)source byteCount:(int64_t)byteCount error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("write(source:byteCount_:)")));
@end

__attribute__((swift_name("OkioBufferedSink")))
@protocol SharedOkioBufferedSink <SharedOkioSink>
@required
- (id<SharedOkioBufferedSink>)emit __attribute__((swift_name("emit()")));
- (id<SharedOkioBufferedSink>)emitCompleteSegments __attribute__((swift_name("emitCompleteSegments()")));
- (id<SharedOkioBufferedSink>)writeSource:(SharedKotlinByteArray *)source __attribute__((swift_name("write(source:)")));
- (id<SharedOkioBufferedSink>)writeByteString:(SharedOkioByteString *)byteString __attribute__((swift_name("write(byteString:)")));
- (id<SharedOkioBufferedSink>)writeSource:(id<SharedOkioSource>)source byteCount:(int64_t)byteCount __attribute__((swift_name("write(source:byteCount:)")));
- (id<SharedOkioBufferedSink>)writeSource:(SharedKotlinByteArray *)source offset:(int32_t)offset byteCount:(int32_t)byteCount __attribute__((swift_name("write(source:offset:byteCount:)")));
- (id<SharedOkioBufferedSink>)writeByteString:(SharedOkioByteString *)byteString offset:(int32_t)offset byteCount:(int32_t)byteCount __attribute__((swift_name("write(byteString:offset:byteCount:)")));
- (int64_t)writeAllSource:(id<SharedOkioSource>)source __attribute__((swift_name("writeAll(source:)")));
- (id<SharedOkioBufferedSink>)writeByteB:(int32_t)b __attribute__((swift_name("writeByte(b:)")));
- (id<SharedOkioBufferedSink>)writeDecimalLongV:(int64_t)v __attribute__((swift_name("writeDecimalLong(v:)")));
- (id<SharedOkioBufferedSink>)writeHexadecimalUnsignedLongV:(int64_t)v __attribute__((swift_name("writeHexadecimalUnsignedLong(v:)")));
- (id<SharedOkioBufferedSink>)writeIntI:(int32_t)i __attribute__((swift_name("writeInt(i:)")));
- (id<SharedOkioBufferedSink>)writeIntLeI:(int32_t)i __attribute__((swift_name("writeIntLe(i:)")));
- (id<SharedOkioBufferedSink>)writeLongV:(int64_t)v __attribute__((swift_name("writeLong(v:)")));
- (id<SharedOkioBufferedSink>)writeLongLeV:(int64_t)v __attribute__((swift_name("writeLongLe(v:)")));
- (id<SharedOkioBufferedSink>)writeShortS:(int32_t)s __attribute__((swift_name("writeShort(s:)")));
- (id<SharedOkioBufferedSink>)writeShortLeS:(int32_t)s __attribute__((swift_name("writeShortLe(s:)")));
- (id<SharedOkioBufferedSink>)writeUtf8String:(NSString *)string __attribute__((swift_name("writeUtf8(string:)")));
- (id<SharedOkioBufferedSink>)writeUtf8String:(NSString *)string beginIndex:(int32_t)beginIndex endIndex:(int32_t)endIndex __attribute__((swift_name("writeUtf8(string:beginIndex:endIndex:)")));
- (id<SharedOkioBufferedSink>)writeUtf8CodePointCodePoint:(int32_t)codePoint __attribute__((swift_name("writeUtf8CodePoint(codePoint:)")));
@property (readonly) SharedOkioBuffer *buffer __attribute__((swift_name("buffer")));
@end

__attribute__((swift_name("Apollo_apiCustomTypeAdapter")))
@protocol SharedApollo_apiCustomTypeAdapter
@required
- (id _Nullable)decodeValue:(SharedApollo_apiCustomTypeValue<id> *)value __attribute__((swift_name("decode(value:)")));
- (SharedApollo_apiCustomTypeValue<id> *)encodeValue:(id _Nullable)value __attribute__((swift_name("encode(value:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiAdapterContext.Builder")))
@interface SharedApollo_apiAdapterContextBuilder : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (SharedApollo_apiAdapterContext *)build __attribute__((swift_name("build()")));
- (SharedApollo_apiAdapterContextBuilder *)mergedDeferredFragmentIdsMergedDeferredFragmentIds:(NSSet<SharedApollo_apiDeferredFragmentIdentifier *> * _Nullable)mergedDeferredFragmentIds __attribute__((swift_name("mergedDeferredFragmentIds(mergedDeferredFragmentIds:)")));
- (SharedApollo_apiAdapterContextBuilder *)serializeVariablesWithDefaultBooleanValuesSerializeVariablesWithDefaultBooleanValues:(SharedBoolean * _Nullable)serializeVariablesWithDefaultBooleanValues __attribute__((swift_name("serializeVariablesWithDefaultBooleanValues(serializeVariablesWithDefaultBooleanValues:)")));
- (SharedApollo_apiAdapterContextBuilder *)variablesVariables:(SharedApollo_apiExecutableVariables * _Nullable)variables __attribute__((swift_name("variables(variables:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiApolloRequestBuilder")))
@interface SharedApollo_apiApolloRequestBuilder<D> : SharedBase <SharedApollo_apiMutableExecutionOptions>
- (instancetype)initWithOperation:(id<SharedApollo_apiOperation>)operation __attribute__((swift_name("init(operation:)"))) __attribute__((objc_designated_initializer));
- (SharedApollo_apiApolloRequestBuilder<D> *)addExecutionContextExecutionContext:(id<SharedApollo_apiExecutionContext>)executionContext __attribute__((swift_name("addExecutionContext(executionContext:)")));
- (SharedApollo_apiApolloRequestBuilder<D> *)addHttpHeaderName:(NSString *)name value:(NSString *)value __attribute__((swift_name("addHttpHeader(name:value:)")));
- (SharedApollo_apiApolloRequest<D> *)build __attribute__((swift_name("build()")));
- (SharedApollo_apiApolloRequestBuilder<D> *)canBeBatchedCanBeBatched:(SharedBoolean * _Nullable)canBeBatched __attribute__((swift_name("canBeBatched(canBeBatched:)")));
- (SharedApollo_apiApolloRequestBuilder<D> *)enableAutoPersistedQueriesEnableAutoPersistedQueries:(SharedBoolean * _Nullable)enableAutoPersistedQueries __attribute__((swift_name("enableAutoPersistedQueries(enableAutoPersistedQueries:)")));
- (SharedApollo_apiApolloRequestBuilder<D> *)executionContextExecutionContext:(id<SharedApollo_apiExecutionContext>)executionContext __attribute__((swift_name("executionContext(executionContext:)")));
- (SharedApollo_apiApolloRequestBuilder<D> *)httpHeadersHttpHeaders:(NSArray<SharedApollo_apiHttpHeader *> * _Nullable)httpHeaders __attribute__((swift_name("httpHeaders(httpHeaders:)")));
- (SharedApollo_apiApolloRequestBuilder<D> *)httpMethodHttpMethod:(SharedApollo_apiHttpMethod * _Nullable)httpMethod __attribute__((swift_name("httpMethod(httpMethod:)")));
- (SharedApollo_apiApolloRequestBuilder<D> *)requestUuidRequestUuid:(SharedUuidUuid *)requestUuid __attribute__((swift_name("requestUuid(requestUuid:)")));
- (SharedApollo_apiApolloRequestBuilder<D> *)sendApqExtensionsSendApqExtensions:(SharedBoolean * _Nullable)sendApqExtensions __attribute__((swift_name("sendApqExtensions(sendApqExtensions:)")));
- (SharedApollo_apiApolloRequestBuilder<D> *)sendDocumentSendDocument:(SharedBoolean * _Nullable)sendDocument __attribute__((swift_name("sendDocument(sendDocument:)")));
@property SharedBoolean * _Nullable canBeBatched __attribute__((swift_name("canBeBatched")));
@property SharedBoolean * _Nullable enableAutoPersistedQueries __attribute__((swift_name("enableAutoPersistedQueries")));
@property id<SharedApollo_apiExecutionContext> executionContext __attribute__((swift_name("executionContext")));
@property NSArray<SharedApollo_apiHttpHeader *> * _Nullable httpHeaders __attribute__((swift_name("httpHeaders")));
@property SharedApollo_apiHttpMethod * _Nullable httpMethod __attribute__((swift_name("httpMethod")));
@property SharedBoolean * _Nullable sendApqExtensions __attribute__((swift_name("sendApqExtensions")));
@property SharedBoolean * _Nullable sendDocument __attribute__((swift_name("sendDocument")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UuidUuid")))
@interface SharedUuidUuid : SharedBase <SharedKotlinComparable>
- (instancetype)initWithUuidBytes:(SharedKotlinByteArray *)uuidBytes __attribute__((swift_name("init(uuidBytes:)"))) __attribute__((objc_designated_initializer)) __attribute__((deprecated("Use `uuidOf` instead.")));
- (instancetype)initWithMsb:(int64_t)msb lsb:(int64_t)lsb __attribute__((swift_name("init(msb:lsb:)"))) __attribute__((objc_designated_initializer));
- (int32_t)compareToOther:(SharedUuidUuid *)other __attribute__((swift_name("compareTo(other:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int64_t leastSignificantBits __attribute__((swift_name("leastSignificantBits")));
@property (readonly) int64_t mostSignificantBits __attribute__((swift_name("mostSignificantBits")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiApolloResponse")))
@interface SharedApollo_apiApolloResponse<D> : SharedBase
- (BOOL)hasErrors __attribute__((swift_name("hasErrors()")));
- (SharedApollo_apiApolloResponseBuilder<D> *)doNewBuilder __attribute__((swift_name("doNewBuilder()")));
@property (readonly) D _Nullable data __attribute__((swift_name("data")));
@property (readonly) D dataAssertNoErrors __attribute__((swift_name("dataAssertNoErrors")));
@property (readonly) NSArray<SharedApollo_apiError *> * _Nullable errors __attribute__((swift_name("errors")));
@property (readonly) id<SharedApollo_apiExecutionContext> executionContext __attribute__((swift_name("executionContext")));
@property (readonly) NSDictionary<NSString *, id> *extensions __attribute__((swift_name("extensions")));
@property (readonly) BOOL isLast __attribute__((swift_name("isLast")));
@property (readonly) id<SharedApollo_apiOperation> operation __attribute__((swift_name("operation")));
@property (readonly) SharedUuidUuid *requestUuid __attribute__((swift_name("requestUuid")));
@end

__attribute__((swift_name("Apollo_runtimeHttpInterceptor")))
@protocol SharedApollo_runtimeHttpInterceptor
@required
- (void)dispose __attribute__((swift_name("dispose()")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)interceptRequest:(SharedApollo_apiHttpRequest *)request chain:(id<SharedApollo_runtimeHttpInterceptorChain>)chain completionHandler:(void (^)(SharedApollo_apiHttpResponse * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("intercept(request:chain:completionHandler:)")));
@end

__attribute__((swift_name("KotlinCoroutineContextElement")))
@protocol SharedKotlinCoroutineContextElement <SharedKotlinCoroutineContext>
@required
@property (readonly) id<SharedKotlinCoroutineContextKey> key __attribute__((swift_name("key")));
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.3")
*/
__attribute__((swift_name("KotlinAbstractCoroutineContextElement")))
@interface SharedKotlinAbstractCoroutineContextElement : SharedBase <SharedKotlinCoroutineContextElement>
- (instancetype)initWithKey:(id<SharedKotlinCoroutineContextKey>)key __attribute__((swift_name("init(key:)"))) __attribute__((objc_designated_initializer));
@property (readonly) id<SharedKotlinCoroutineContextKey> key __attribute__((swift_name("key")));
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.3")
*/
__attribute__((swift_name("KotlinContinuationInterceptor")))
@protocol SharedKotlinContinuationInterceptor <SharedKotlinCoroutineContextElement>
@required
- (id<SharedKotlinContinuation>)interceptContinuationContinuation:(id<SharedKotlinContinuation>)continuation __attribute__((swift_name("interceptContinuation(continuation:)")));
- (void)releaseInterceptedContinuationContinuation:(id<SharedKotlinContinuation>)continuation __attribute__((swift_name("releaseInterceptedContinuation(continuation:)")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreCoroutineDispatcher")))
@interface SharedKotlinx_coroutines_coreCoroutineDispatcher : SharedKotlinAbstractCoroutineContextElement <SharedKotlinContinuationInterceptor>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithKey:(id<SharedKotlinCoroutineContextKey>)key __attribute__((swift_name("init(key:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly, getter=companion) SharedKotlinx_coroutines_coreCoroutineDispatcherKey *companion __attribute__((swift_name("companion")));
- (void)dispatchContext:(id<SharedKotlinCoroutineContext>)context block:(id<SharedKotlinx_coroutines_coreRunnable>)block __attribute__((swift_name("dispatch(context:block:)")));

/**
 * @note annotations
 *   kotlinx.coroutines.InternalCoroutinesApi
*/
- (void)dispatchYieldContext:(id<SharedKotlinCoroutineContext>)context block:(id<SharedKotlinx_coroutines_coreRunnable>)block __attribute__((swift_name("dispatchYield(context:block:)")));
- (id<SharedKotlinContinuation>)interceptContinuationContinuation:(id<SharedKotlinContinuation>)continuation __attribute__((swift_name("interceptContinuation(continuation:)")));
- (BOOL)isDispatchNeededContext:(id<SharedKotlinCoroutineContext>)context __attribute__((swift_name("isDispatchNeeded(context:)")));
- (SharedKotlinx_coroutines_coreCoroutineDispatcher *)limitedParallelismParallelism:(int32_t)parallelism name:(NSString * _Nullable)name __attribute__((swift_name("limitedParallelism(parallelism:name:)")));
- (SharedKotlinx_coroutines_coreCoroutineDispatcher *)plusOther:(SharedKotlinx_coroutines_coreCoroutineDispatcher *)other __attribute__((swift_name("plus(other:)"))) __attribute__((unavailable("Operator '+' on two CoroutineDispatcher objects is meaningless. CoroutineDispatcher is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The dispatcher to the right of `+` just replaces the dispatcher to the left.")));
- (void)releaseInterceptedContinuationContinuation:(id<SharedKotlinContinuation>)continuation __attribute__((swift_name("releaseInterceptedContinuation(continuation:)")));
- (NSString *)description __attribute__((swift_name("description()")));
@end

__attribute__((swift_name("Apollo_runtimeHttpEngine")))
@protocol SharedApollo_runtimeHttpEngine
@required
- (void)dispose __attribute__((swift_name("dispose()")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)executeRequest:(SharedApollo_apiHttpRequest *)request completionHandler:(void (^)(SharedApollo_apiHttpResponse * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("execute(request:completionHandler:)")));
@end

__attribute__((swift_name("Apollo_runtimeWebSocketEngine")))
@protocol SharedApollo_runtimeWebSocketEngine
@required

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)openUrl:(NSString *)url headers:(NSArray<SharedApollo_apiHttpHeader *> *)headers completionHandler:(void (^)(id<SharedApollo_runtimeWebSocketConnection> _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("open(url:headers:completionHandler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)openUrl:(NSString *)url headers:(NSDictionary<NSString *, NSString *> *)headers completionHandler_:(void (^)(id<SharedApollo_runtimeWebSocketConnection> _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("open(url:headers:completionHandler_:)"))) __attribute__((deprecated("Use open(String, List<HttpHeader>) instead.")));
@end

__attribute__((swift_name("KotlinFunction")))
@protocol SharedKotlinFunction
@required
@end

__attribute__((swift_name("KotlinSuspendFunction2")))
@protocol SharedKotlinSuspendFunction2 <SharedKotlinFunction>
@required

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)invokeP1:(id _Nullable)p1 p2:(id _Nullable)p2 completionHandler:(void (^)(id _Nullable_result, NSError * _Nullable))completionHandler __attribute__((swift_name("invoke(p1:p2:completionHandler:)")));
@end

__attribute__((swift_name("KotlinSuspendFunction0")))
@protocol SharedKotlinSuspendFunction0 <SharedKotlinFunction>
@required

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)invokeWithCompletionHandler:(void (^)(id _Nullable_result, NSError * _Nullable))completionHandler __attribute__((swift_name("invoke(completionHandler:)")));
@end

__attribute__((swift_name("Apollo_runtimeWsProtocolFactory")))
@protocol SharedApollo_runtimeWsProtocolFactory
@required
- (SharedApollo_runtimeWsProtocol *)createWebSocketConnection:(id<SharedApollo_runtimeWebSocketConnection>)webSocketConnection listener:(id<SharedApollo_runtimeWsProtocolListener>)listener scope:(id<SharedKotlinx_coroutines_coreCoroutineScope>)scope __attribute__((swift_name("create(webSocketConnection:listener:scope:)")));
@property (readonly, getter=name_) NSString *name __attribute__((swift_name("name")));
@end

__attribute__((swift_name("Apollo_runtimeApolloInterceptorChain")))
@protocol SharedApollo_runtimeApolloInterceptorChain
@required
- (id<SharedKotlinx_coroutines_coreFlow>)proceedRequest:(SharedApollo_apiApolloRequest<id<SharedApollo_apiOperationData>> *)request __attribute__((swift_name("proceed(request:)")));
@end

__attribute__((swift_name("KotlinCoroutineContextKey")))
@protocol SharedKotlinCoroutineContextKey
@required
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinByteArray")))
@interface SharedKotlinByteArray : SharedBase
+ (instancetype)arrayWithSize:(int32_t)size __attribute__((swift_name("init(size:)")));
+ (instancetype)arrayWithSize:(int32_t)size init:(SharedByte *(^)(SharedInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (int8_t)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (SharedKotlinByteIterator *)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(int8_t)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size __attribute__((swift_name("size")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiInterfaceType.Builder")))
@interface SharedApollo_apiInterfaceTypeBuilder : SharedBase
- (instancetype)initWithInterfaceType:(SharedApollo_apiInterfaceType *)interfaceType __attribute__((swift_name("init(interfaceType:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithName:(NSString *)name __attribute__((swift_name("init(name:)"))) __attribute__((objc_designated_initializer));
- (SharedApollo_apiInterfaceType *)build __attribute__((swift_name("build()")));
- (SharedApollo_apiInterfaceTypeBuilder *)embeddedFieldsEmbeddedFields:(NSArray<NSString *> *)embeddedFields __attribute__((swift_name("embeddedFields(embeddedFields:)")));
- (SharedApollo_apiInterfaceTypeBuilder *)interfacesImplements:(NSArray<SharedApollo_apiInterfaceType *> *)implements __attribute__((swift_name("interfaces(implements:)")));
- (SharedApollo_apiInterfaceTypeBuilder *)keyFieldsKeyFields:(NSArray<NSString *> *)keyFields __attribute__((swift_name("keyFields(keyFields:)")));
@end

__attribute__((swift_name("KotlinIntIterator")))
@interface SharedKotlinIntIterator : SharedBase <SharedKotlinIterator>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (SharedInt *)next __attribute__((swift_name("next()")));
- (int32_t)nextInt __attribute__((swift_name("nextInt_()")));
@end

__attribute__((swift_name("OkioByteString")))
@interface SharedOkioByteString : SharedBase <SharedKotlinComparable>
@property (class, readonly, getter=companion) SharedOkioByteStringCompanion *companion __attribute__((swift_name("companion")));
- (NSString *)base64 __attribute__((swift_name("base64()")));
- (NSString *)base64Url __attribute__((swift_name("base64Url()")));
- (int32_t)compareToOther:(SharedOkioByteString *)other __attribute__((swift_name("compareTo(other:)")));
- (void)doCopyIntoOffset:(int32_t)offset target:(SharedKotlinByteArray *)target targetOffset:(int32_t)targetOffset byteCount:(int32_t)byteCount __attribute__((swift_name("doCopyInto(offset:target:targetOffset:byteCount:)")));
- (BOOL)endsWithSuffix:(SharedKotlinByteArray *)suffix __attribute__((swift_name("endsWith(suffix:)")));
- (BOOL)endsWithSuffix_:(SharedOkioByteString *)suffix __attribute__((swift_name("endsWith(suffix_:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (int8_t)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)hex __attribute__((swift_name("hex()")));
- (SharedOkioByteString *)hmacSha1Key:(SharedOkioByteString *)key __attribute__((swift_name("hmacSha1(key:)")));
- (SharedOkioByteString *)hmacSha256Key:(SharedOkioByteString *)key __attribute__((swift_name("hmacSha256(key:)")));
- (SharedOkioByteString *)hmacSha512Key:(SharedOkioByteString *)key __attribute__((swift_name("hmacSha512(key:)")));
- (int32_t)indexOfOther:(SharedKotlinByteArray *)other fromIndex:(int32_t)fromIndex __attribute__((swift_name("indexOf(other:fromIndex:)")));
- (int32_t)indexOfOther:(SharedOkioByteString *)other fromIndex_:(int32_t)fromIndex __attribute__((swift_name("indexOf(other:fromIndex_:)")));
- (int32_t)lastIndexOfOther:(SharedKotlinByteArray *)other fromIndex:(int32_t)fromIndex __attribute__((swift_name("lastIndexOf(other:fromIndex:)")));
- (int32_t)lastIndexOfOther:(SharedOkioByteString *)other fromIndex_:(int32_t)fromIndex __attribute__((swift_name("lastIndexOf(other:fromIndex_:)")));
- (SharedOkioByteString *)md5 __attribute__((swift_name("md5()")));
- (BOOL)rangeEqualsOffset:(int32_t)offset other:(SharedKotlinByteArray *)other otherOffset:(int32_t)otherOffset byteCount:(int32_t)byteCount __attribute__((swift_name("rangeEquals(offset:other:otherOffset:byteCount:)")));
- (BOOL)rangeEqualsOffset:(int32_t)offset other:(SharedOkioByteString *)other otherOffset:(int32_t)otherOffset byteCount_:(int32_t)byteCount __attribute__((swift_name("rangeEquals(offset:other:otherOffset:byteCount_:)")));
- (SharedOkioByteString *)sha1 __attribute__((swift_name("sha1()")));
- (SharedOkioByteString *)sha256 __attribute__((swift_name("sha256()")));
- (SharedOkioByteString *)sha512 __attribute__((swift_name("sha512()")));
- (BOOL)startsWithPrefix:(SharedKotlinByteArray *)prefix __attribute__((swift_name("startsWith(prefix:)")));
- (BOOL)startsWithPrefix_:(SharedOkioByteString *)prefix __attribute__((swift_name("startsWith(prefix_:)")));
- (SharedOkioByteString *)substringBeginIndex:(int32_t)beginIndex endIndex:(int32_t)endIndex __attribute__((swift_name("substring(beginIndex:endIndex:)")));
- (SharedOkioByteString *)toAsciiLowercase __attribute__((swift_name("toAsciiLowercase()")));
- (SharedOkioByteString *)toAsciiUppercase __attribute__((swift_name("toAsciiUppercase()")));
- (SharedKotlinByteArray *)toByteArray __attribute__((swift_name("toByteArray()")));
- (NSString *)description __attribute__((swift_name("description()")));
- (NSString *)utf8 __attribute__((swift_name("utf8()")));
@property (readonly) int32_t size __attribute__((swift_name("size")));
@end

__attribute__((swift_name("OkioSource")))
@protocol SharedOkioSource <SharedOkioCloseable>
@required

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (int64_t)readSink:(SharedOkioBuffer *)sink byteCount:(int64_t)byteCount error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("read(sink:byteCount:)"))) __attribute__((swift_error(nonnull_error)));
- (SharedOkioTimeout *)timeout __attribute__((swift_name("timeout()")));
@end

__attribute__((swift_name("OkioBufferedSource")))
@protocol SharedOkioBufferedSource <SharedOkioSource>
@required
- (BOOL)exhausted __attribute__((swift_name("exhausted()")));
- (int64_t)indexOfB:(int8_t)b __attribute__((swift_name("indexOf(b:)")));
- (int64_t)indexOfBytes:(SharedOkioByteString *)bytes __attribute__((swift_name("indexOf(bytes:)")));
- (int64_t)indexOfB:(int8_t)b fromIndex:(int64_t)fromIndex __attribute__((swift_name("indexOf(b:fromIndex:)")));
- (int64_t)indexOfBytes:(SharedOkioByteString *)bytes fromIndex:(int64_t)fromIndex __attribute__((swift_name("indexOf(bytes:fromIndex:)")));
- (int64_t)indexOfB:(int8_t)b fromIndex:(int64_t)fromIndex toIndex:(int64_t)toIndex __attribute__((swift_name("indexOf(b:fromIndex:toIndex:)")));
- (int64_t)indexOfElementTargetBytes:(SharedOkioByteString *)targetBytes __attribute__((swift_name("indexOfElement(targetBytes:)")));
- (int64_t)indexOfElementTargetBytes:(SharedOkioByteString *)targetBytes fromIndex:(int64_t)fromIndex __attribute__((swift_name("indexOfElement(targetBytes:fromIndex:)")));
- (id<SharedOkioBufferedSource>)peek __attribute__((swift_name("peek_()")));
- (BOOL)rangeEqualsOffset:(int64_t)offset bytes:(SharedOkioByteString *)bytes __attribute__((swift_name("rangeEquals(offset:bytes:)")));
- (BOOL)rangeEqualsOffset:(int64_t)offset bytes:(SharedOkioByteString *)bytes bytesOffset:(int32_t)bytesOffset byteCount:(int32_t)byteCount __attribute__((swift_name("rangeEquals(offset:bytes:bytesOffset:byteCount:)")));
- (int32_t)readSink:(SharedKotlinByteArray *)sink __attribute__((swift_name("read(sink:)")));
- (int32_t)readSink:(SharedKotlinByteArray *)sink offset:(int32_t)offset byteCount:(int32_t)byteCount __attribute__((swift_name("read(sink:offset:byteCount:)")));
- (int64_t)readAllSink:(id<SharedOkioSink>)sink __attribute__((swift_name("readAll(sink:)")));
- (int8_t)readByte __attribute__((swift_name("readByte()")));
- (SharedKotlinByteArray *)readByteArray __attribute__((swift_name("readByteArray()")));
- (SharedKotlinByteArray *)readByteArrayByteCount:(int64_t)byteCount __attribute__((swift_name("readByteArray(byteCount:)")));
- (SharedOkioByteString *)readByteString __attribute__((swift_name("readByteString()")));
- (SharedOkioByteString *)readByteStringByteCount:(int64_t)byteCount __attribute__((swift_name("readByteString(byteCount:)")));
- (int64_t)readDecimalLong __attribute__((swift_name("readDecimalLong()")));
- (void)readFullySink:(SharedKotlinByteArray *)sink __attribute__((swift_name("readFully(sink:)")));
- (void)readFullySink:(SharedOkioBuffer *)sink byteCount:(int64_t)byteCount __attribute__((swift_name("readFully(sink:byteCount:)")));
- (int64_t)readHexadecimalUnsignedLong __attribute__((swift_name("readHexadecimalUnsignedLong()")));
- (int32_t)readInt __attribute__((swift_name("readInt()")));
- (int32_t)readIntLe __attribute__((swift_name("readIntLe()")));
- (int64_t)readLong __attribute__((swift_name("readLong()")));
- (int64_t)readLongLe __attribute__((swift_name("readLongLe()")));
- (int16_t)readShort __attribute__((swift_name("readShort()")));
- (int16_t)readShortLe __attribute__((swift_name("readShortLe()")));
- (NSString *)readUtf8 __attribute__((swift_name("readUtf8()")));
- (NSString *)readUtf8ByteCount:(int64_t)byteCount __attribute__((swift_name("readUtf8(byteCount:)")));
- (int32_t)readUtf8CodePoint __attribute__((swift_name("readUtf8CodePoint()")));
- (NSString * _Nullable)readUtf8Line __attribute__((swift_name("readUtf8Line()")));
- (NSString *)readUtf8LineStrict __attribute__((swift_name("readUtf8LineStrict()")));
- (NSString *)readUtf8LineStrictLimit:(int64_t)limit __attribute__((swift_name("readUtf8LineStrict(limit:)")));
- (BOOL)requestByteCount:(int64_t)byteCount __attribute__((swift_name("request(byteCount:)")));
- (void)requireByteCount:(int64_t)byteCount __attribute__((swift_name("require(byteCount:)")));
- (int32_t)selectOptions:(NSArray<SharedOkioByteString *> *)options __attribute__((swift_name("select(options:)")));
- (id _Nullable)selectOptions_:(NSArray<id> *)options __attribute__((swift_name("select(options_:)")));
- (void)skipByteCount:(int64_t)byteCount __attribute__((swift_name("skip(byteCount:)")));
@property (readonly) SharedOkioBuffer *buffer __attribute__((swift_name("buffer")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("OkioBuffer")))
@interface SharedOkioBuffer : SharedBase <SharedOkioBufferedSource, SharedOkioBufferedSink>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)clear __attribute__((swift_name("clear()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)closeAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("close_()")));
- (int64_t)completeSegmentByteCount __attribute__((swift_name("completeSegmentByteCount()")));
- (SharedOkioBuffer *)doCopy __attribute__((swift_name("doCopy()")));
- (SharedOkioBuffer *)doCopyToOut:(SharedOkioBuffer *)out offset:(int64_t)offset __attribute__((swift_name("doCopyTo(out:offset:)")));
- (SharedOkioBuffer *)doCopyToOut:(SharedOkioBuffer *)out offset:(int64_t)offset byteCount:(int64_t)byteCount __attribute__((swift_name("doCopyTo(out:offset:byteCount:)")));
- (SharedOkioBuffer *)emit __attribute__((swift_name("emit()")));
- (SharedOkioBuffer *)emitCompleteSegments __attribute__((swift_name("emitCompleteSegments()")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (BOOL)exhausted __attribute__((swift_name("exhausted()")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)flushAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("flush()")));
- (int8_t)getPos:(int64_t)pos __attribute__((swift_name("get(pos:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (SharedOkioByteString *)hmacSha1Key:(SharedOkioByteString *)key __attribute__((swift_name("hmacSha1(key:)")));
- (SharedOkioByteString *)hmacSha256Key:(SharedOkioByteString *)key __attribute__((swift_name("hmacSha256(key:)")));
- (SharedOkioByteString *)hmacSha512Key:(SharedOkioByteString *)key __attribute__((swift_name("hmacSha512(key:)")));
- (int64_t)indexOfB:(int8_t)b __attribute__((swift_name("indexOf(b:)")));
- (int64_t)indexOfBytes:(SharedOkioByteString *)bytes __attribute__((swift_name("indexOf(bytes:)")));
- (int64_t)indexOfB:(int8_t)b fromIndex:(int64_t)fromIndex __attribute__((swift_name("indexOf(b:fromIndex:)")));
- (int64_t)indexOfBytes:(SharedOkioByteString *)bytes fromIndex:(int64_t)fromIndex __attribute__((swift_name("indexOf(bytes:fromIndex:)")));
- (int64_t)indexOfB:(int8_t)b fromIndex:(int64_t)fromIndex toIndex:(int64_t)toIndex __attribute__((swift_name("indexOf(b:fromIndex:toIndex:)")));
- (int64_t)indexOfElementTargetBytes:(SharedOkioByteString *)targetBytes __attribute__((swift_name("indexOfElement(targetBytes:)")));
- (int64_t)indexOfElementTargetBytes:(SharedOkioByteString *)targetBytes fromIndex:(int64_t)fromIndex __attribute__((swift_name("indexOfElement(targetBytes:fromIndex:)")));
- (SharedOkioByteString *)md5 __attribute__((swift_name("md5()")));
- (id<SharedOkioBufferedSource>)peek __attribute__((swift_name("peek_()")));
- (BOOL)rangeEqualsOffset:(int64_t)offset bytes:(SharedOkioByteString *)bytes __attribute__((swift_name("rangeEquals(offset:bytes:)")));
- (BOOL)rangeEqualsOffset:(int64_t)offset bytes:(SharedOkioByteString *)bytes bytesOffset:(int32_t)bytesOffset byteCount:(int32_t)byteCount __attribute__((swift_name("rangeEquals(offset:bytes:bytesOffset:byteCount:)")));
- (int32_t)readSink:(SharedKotlinByteArray *)sink __attribute__((swift_name("read(sink:)")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (int64_t)readSink:(SharedOkioBuffer *)sink byteCount:(int64_t)byteCount error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("read(sink:byteCount:)"))) __attribute__((swift_error(nonnull_error)));
- (int32_t)readSink:(SharedKotlinByteArray *)sink offset:(int32_t)offset byteCount:(int32_t)byteCount __attribute__((swift_name("read(sink:offset:byteCount:)")));
- (int64_t)readAllSink:(id<SharedOkioSink>)sink __attribute__((swift_name("readAll(sink:)")));
- (SharedOkioBufferUnsafeCursor *)readAndWriteUnsafeUnsafeCursor:(SharedOkioBufferUnsafeCursor *)unsafeCursor __attribute__((swift_name("readAndWriteUnsafe(unsafeCursor:)")));
- (int8_t)readByte __attribute__((swift_name("readByte()")));
- (SharedKotlinByteArray *)readByteArray __attribute__((swift_name("readByteArray()")));
- (SharedKotlinByteArray *)readByteArrayByteCount:(int64_t)byteCount __attribute__((swift_name("readByteArray(byteCount:)")));
- (SharedOkioByteString *)readByteString __attribute__((swift_name("readByteString()")));
- (SharedOkioByteString *)readByteStringByteCount:(int64_t)byteCount __attribute__((swift_name("readByteString(byteCount:)")));
- (int64_t)readDecimalLong __attribute__((swift_name("readDecimalLong()")));
- (void)readFullySink:(SharedKotlinByteArray *)sink __attribute__((swift_name("readFully(sink:)")));
- (void)readFullySink:(SharedOkioBuffer *)sink byteCount:(int64_t)byteCount __attribute__((swift_name("readFully(sink:byteCount:)")));
- (int64_t)readHexadecimalUnsignedLong __attribute__((swift_name("readHexadecimalUnsignedLong()")));
- (int32_t)readInt __attribute__((swift_name("readInt()")));
- (int32_t)readIntLe __attribute__((swift_name("readIntLe()")));
- (int64_t)readLong __attribute__((swift_name("readLong()")));
- (int64_t)readLongLe __attribute__((swift_name("readLongLe()")));
- (int16_t)readShort __attribute__((swift_name("readShort()")));
- (int16_t)readShortLe __attribute__((swift_name("readShortLe()")));
- (SharedOkioBufferUnsafeCursor *)readUnsafeUnsafeCursor:(SharedOkioBufferUnsafeCursor *)unsafeCursor __attribute__((swift_name("readUnsafe(unsafeCursor:)")));
- (NSString *)readUtf8 __attribute__((swift_name("readUtf8()")));
- (NSString *)readUtf8ByteCount:(int64_t)byteCount __attribute__((swift_name("readUtf8(byteCount:)")));
- (int32_t)readUtf8CodePoint __attribute__((swift_name("readUtf8CodePoint()")));
- (NSString * _Nullable)readUtf8Line __attribute__((swift_name("readUtf8Line()")));
- (NSString *)readUtf8LineStrict __attribute__((swift_name("readUtf8LineStrict()")));
- (NSString *)readUtf8LineStrictLimit:(int64_t)limit __attribute__((swift_name("readUtf8LineStrict(limit:)")));
- (BOOL)requestByteCount:(int64_t)byteCount __attribute__((swift_name("request(byteCount:)")));
- (void)requireByteCount:(int64_t)byteCount __attribute__((swift_name("require(byteCount:)")));
- (int32_t)selectOptions:(NSArray<SharedOkioByteString *> *)options __attribute__((swift_name("select(options:)")));
- (id _Nullable)selectOptions_:(NSArray<id> *)options __attribute__((swift_name("select(options_:)")));
- (SharedOkioByteString *)sha1 __attribute__((swift_name("sha1()")));
- (SharedOkioByteString *)sha256 __attribute__((swift_name("sha256()")));
- (SharedOkioByteString *)sha512 __attribute__((swift_name("sha512()")));
- (void)skipByteCount:(int64_t)byteCount __attribute__((swift_name("skip(byteCount:)")));
- (SharedOkioByteString *)snapshot __attribute__((swift_name("snapshot()")));
- (SharedOkioByteString *)snapshotByteCount:(int32_t)byteCount __attribute__((swift_name("snapshot(byteCount:)")));
- (SharedOkioTimeout *)timeout __attribute__((swift_name("timeout()")));
- (NSString *)description __attribute__((swift_name("description()")));
- (SharedOkioBuffer *)writeSource:(SharedKotlinByteArray *)source __attribute__((swift_name("write(source:)")));
- (SharedOkioBuffer *)writeByteString:(SharedOkioByteString *)byteString __attribute__((swift_name("write(byteString:)")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)writeSource:(SharedOkioBuffer *)source byteCount:(int64_t)byteCount error:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("write(source:byteCount_:)")));
- (SharedOkioBuffer *)writeSource:(id<SharedOkioSource>)source byteCount:(int64_t)byteCount __attribute__((swift_name("write(source:byteCount:)")));
- (SharedOkioBuffer *)writeSource:(SharedKotlinByteArray *)source offset:(int32_t)offset byteCount:(int32_t)byteCount __attribute__((swift_name("write(source:offset:byteCount:)")));
- (SharedOkioBuffer *)writeByteString:(SharedOkioByteString *)byteString offset:(int32_t)offset byteCount:(int32_t)byteCount __attribute__((swift_name("write(byteString:offset:byteCount:)")));
- (int64_t)writeAllSource:(id<SharedOkioSource>)source __attribute__((swift_name("writeAll(source:)")));
- (SharedOkioBuffer *)writeByteB:(int32_t)b __attribute__((swift_name("writeByte(b:)")));
- (SharedOkioBuffer *)writeDecimalLongV:(int64_t)v __attribute__((swift_name("writeDecimalLong(v:)")));
- (SharedOkioBuffer *)writeHexadecimalUnsignedLongV:(int64_t)v __attribute__((swift_name("writeHexadecimalUnsignedLong(v:)")));
- (SharedOkioBuffer *)writeIntI:(int32_t)i __attribute__((swift_name("writeInt(i:)")));
- (SharedOkioBuffer *)writeIntLeI:(int32_t)i __attribute__((swift_name("writeIntLe(i:)")));
- (SharedOkioBuffer *)writeLongV:(int64_t)v __attribute__((swift_name("writeLong(v:)")));
- (SharedOkioBuffer *)writeLongLeV:(int64_t)v __attribute__((swift_name("writeLongLe(v:)")));
- (SharedOkioBuffer *)writeShortS:(int32_t)s __attribute__((swift_name("writeShort(s:)")));
- (SharedOkioBuffer *)writeShortLeS:(int32_t)s __attribute__((swift_name("writeShortLe(s:)")));
- (SharedOkioBuffer *)writeUtf8String:(NSString *)string __attribute__((swift_name("writeUtf8(string:)")));
- (SharedOkioBuffer *)writeUtf8String:(NSString *)string beginIndex:(int32_t)beginIndex endIndex:(int32_t)endIndex __attribute__((swift_name("writeUtf8(string:beginIndex:endIndex:)")));
- (SharedOkioBuffer *)writeUtf8CodePointCodePoint:(int32_t)codePoint __attribute__((swift_name("writeUtf8CodePoint(codePoint:)")));
@property (readonly) SharedOkioBuffer *buffer __attribute__((swift_name("buffer")));
@property (readonly) int64_t size __attribute__((swift_name("size")));
@end

__attribute__((swift_name("OkioTimeout")))
@interface SharedOkioTimeout : SharedBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedOkioTimeoutCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((swift_name("Apollo_apiCustomTypeValue")))
@interface SharedApollo_apiCustomTypeValue<T> : SharedBase
@property (class, readonly, getter=companion) SharedApollo_apiCustomTypeValueCompanion *companion __attribute__((swift_name("companion")));
@property (readonly) T _Nullable value __attribute__((swift_name("value")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiDeferredFragmentIdentifier")))
@interface SharedApollo_apiDeferredFragmentIdentifier : SharedBase
- (instancetype)initWithPath:(NSArray<id> *)path label:(NSString * _Nullable)label __attribute__((swift_name("init(path:label:)"))) __attribute__((objc_designated_initializer));
- (SharedApollo_apiDeferredFragmentIdentifier *)doCopyPath:(NSArray<id> *)path label:(NSString * _Nullable)label __attribute__((swift_name("doCopy(path:label:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString * _Nullable label __attribute__((swift_name("label")));
@property (readonly) NSArray<id> *path __attribute__((swift_name("path")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiApolloResponseBuilder")))
@interface SharedApollo_apiApolloResponseBuilder<D> : SharedBase
- (instancetype)initWithOperation:(id<SharedApollo_apiOperation>)operation requestUuid:(SharedUuidUuid *)requestUuid data:(D _Nullable)data __attribute__((swift_name("init(operation:requestUuid:data:)"))) __attribute__((objc_designated_initializer));
- (SharedApollo_apiApolloResponseBuilder<D> *)addExecutionContextExecutionContext:(id<SharedApollo_apiExecutionContext>)executionContext __attribute__((swift_name("addExecutionContext(executionContext:)")));
- (SharedApollo_apiApolloResponse<D> *)build __attribute__((swift_name("build()")));
- (SharedApollo_apiApolloResponseBuilder<D> *)errorsErrors:(NSArray<SharedApollo_apiError *> * _Nullable)errors __attribute__((swift_name("errors(errors:)")));
- (SharedApollo_apiApolloResponseBuilder<D> *)extensionsExtensions:(NSDictionary<NSString *, id> * _Nullable)extensions __attribute__((swift_name("extensions(extensions:)")));
- (SharedApollo_apiApolloResponseBuilder<D> *)isLastIsLast:(BOOL)isLast __attribute__((swift_name("isLast(isLast:)")));
- (SharedApollo_apiApolloResponseBuilder<D> *)requestUuidRequestUuid:(SharedUuidUuid *)requestUuid __attribute__((swift_name("requestUuid(requestUuid:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiError")))
@interface SharedApollo_apiError : SharedBase
- (instancetype)initWithMessage:(NSString *)message locations:(NSArray<SharedApollo_apiErrorLocation *> * _Nullable)locations path:(NSArray<id> * _Nullable)path extensions:(NSDictionary<NSString *, id> * _Nullable)extensions nonStandardFields:(NSDictionary<NSString *, id> * _Nullable)nonStandardFields __attribute__((swift_name("init(message:locations:path:extensions:nonStandardFields:)"))) __attribute__((objc_designated_initializer));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSDictionary<NSString *, id> *customAttributes __attribute__((swift_name("customAttributes"))) __attribute__((unavailable("Used for backward compatibility with 2.x")));
@property (readonly) NSDictionary<NSString *, id> * _Nullable extensions __attribute__((swift_name("extensions")));
@property (readonly) NSArray<SharedApollo_apiErrorLocation *> * _Nullable locations __attribute__((swift_name("locations")));
@property (readonly) NSString *message __attribute__((swift_name("message")));
@property (readonly) NSDictionary<NSString *, id> * _Nullable nonStandardFields __attribute__((swift_name("nonStandardFields")));
@property (readonly) NSArray<id> * _Nullable path __attribute__((swift_name("path")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiHttpRequest")))
@interface SharedApollo_apiHttpRequest : SharedBase

/**
 * @note annotations
 *   kotlin.jvm.JvmOverloads
*/
- (SharedApollo_apiHttpRequestBuilder *)doNewBuilderMethod:(SharedApollo_apiHttpMethod *)method url:(NSString *)url __attribute__((swift_name("doNewBuilder(method:url:)")));
@property (readonly) id<SharedApollo_apiHttpBody> _Nullable body __attribute__((swift_name("body")));
@property (readonly) NSArray<SharedApollo_apiHttpHeader *> *headers __attribute__((swift_name("headers")));
@property (readonly) SharedApollo_apiHttpMethod *method __attribute__((swift_name("method")));
@property (readonly) NSString *url __attribute__((swift_name("url")));
@end

__attribute__((swift_name("Apollo_runtimeHttpInterceptorChain")))
@protocol SharedApollo_runtimeHttpInterceptorChain
@required

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)proceedRequest:(SharedApollo_apiHttpRequest *)request completionHandler:(void (^)(SharedApollo_apiHttpResponse * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("proceed(request:completionHandler:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiHttpResponse")))
@interface SharedApollo_apiHttpResponse : SharedBase
- (SharedApollo_apiHttpResponseBuilder *)doNewBuilder __attribute__((swift_name("doNewBuilder()")));
@property (readonly) id<SharedOkioBufferedSource> _Nullable body __attribute__((swift_name("body")));
@property (readonly) NSArray<SharedApollo_apiHttpHeader *> *headers __attribute__((swift_name("headers")));
@property (readonly) int32_t statusCode __attribute__((swift_name("statusCode")));
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.3")
*/
__attribute__((swift_name("KotlinContinuation")))
@protocol SharedKotlinContinuation
@required
- (void)resumeWithResult:(id _Nullable)result __attribute__((swift_name("resumeWith(result:)")));
@property (readonly) id<SharedKotlinCoroutineContext> context __attribute__((swift_name("context")));
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.3")
 *   kotlin.ExperimentalStdlibApi
*/
__attribute__((swift_name("KotlinAbstractCoroutineContextKey")))
@interface SharedKotlinAbstractCoroutineContextKey<B, E> : SharedBase <SharedKotlinCoroutineContextKey>
- (instancetype)initWithBaseKey:(id<SharedKotlinCoroutineContextKey>)baseKey safeCast:(E _Nullable (^)(id<SharedKotlinCoroutineContextElement>))safeCast __attribute__((swift_name("init(baseKey:safeCast:)"))) __attribute__((objc_designated_initializer));
@end


/**
 * @note annotations
 *   kotlin.ExperimentalStdlibApi
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Kotlinx_coroutines_coreCoroutineDispatcher.Key")))
@interface SharedKotlinx_coroutines_coreCoroutineDispatcherKey : SharedKotlinAbstractCoroutineContextKey<id<SharedKotlinContinuationInterceptor>, SharedKotlinx_coroutines_coreCoroutineDispatcher *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithBaseKey:(id<SharedKotlinCoroutineContextKey>)baseKey safeCast:(id<SharedKotlinCoroutineContextElement> _Nullable (^)(id<SharedKotlinCoroutineContextElement>))safeCast __attribute__((swift_name("init(baseKey:safeCast:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
+ (instancetype)key __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedKotlinx_coroutines_coreCoroutineDispatcherKey *shared __attribute__((swift_name("shared")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreRunnable")))
@protocol SharedKotlinx_coroutines_coreRunnable
@required
- (void)run __attribute__((swift_name("run()")));
@end

__attribute__((swift_name("Apollo_runtimeWebSocketConnection")))
@protocol SharedApollo_runtimeWebSocketConnection
@required
- (void)close __attribute__((swift_name("close()")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)receiveWithCompletionHandler:(void (^)(NSString * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("receive(completionHandler:)")));
- (void)sendString:(NSString *)string __attribute__((swift_name("send(string:)")));
- (void)sendData:(SharedOkioByteString *)data __attribute__((swift_name("send(data:)")));
@end

__attribute__((swift_name("Apollo_runtimeWsProtocol")))
@interface SharedApollo_runtimeWsProtocol : SharedBase
- (instancetype)initWithWebSocketConnection:(id<SharedApollo_runtimeWebSocketConnection>)webSocketConnection listener:(id<SharedApollo_runtimeWsProtocolListener>)listener __attribute__((swift_name("init(webSocketConnection:listener:)"))) __attribute__((objc_designated_initializer));
- (void)close __attribute__((swift_name("close()")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)connectionInitWithCompletionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("connectionInit(completionHandler:)")));
- (void)handleServerMessageMessageMap:(NSDictionary<NSString *, id> *)messageMap __attribute__((swift_name("handleServerMessage(messageMap:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (void)receiveMessageMapWithCompletionHandler:(void (^)(NSDictionary<NSString *, id> * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("receiveMessageMap(completionHandler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)runWithCompletionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("run(completionHandler:)")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (void)sendMessageMapMessageMap:(NSDictionary<NSString *, id> *)messageMap frameType:(SharedApollo_runtimeWsFrameType *)frameType __attribute__((swift_name("sendMessageMap(messageMap:frameType:)")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (void)sendMessageMapBinaryMessageMap:(NSDictionary<NSString *, id> *)messageMap __attribute__((swift_name("sendMessageMapBinary(messageMap:)")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (void)sendMessageMapTextMessageMap:(NSDictionary<NSString *, id> *)messageMap __attribute__((swift_name("sendMessageMapText(messageMap:)")));
- (void)startOperationRequest:(SharedApollo_apiApolloRequest<id<SharedApollo_apiOperationData>> *)request __attribute__((swift_name("startOperation(request:)")));
- (void)stopOperationRequest:(SharedApollo_apiApolloRequest<id<SharedApollo_apiOperationData>> *)request __attribute__((swift_name("stopOperation(request:)")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (SharedOkioByteString *)toByteString:(NSDictionary<NSString *, id> *)receiver __attribute__((swift_name("toByteString(_:)")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (NSDictionary<NSString *, id> * _Nullable)toMessageMap:(NSString *)receiver __attribute__((swift_name("toMessageMap(_:)")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (NSString *)toUtf8:(NSDictionary<NSString *, id> *)receiver __attribute__((swift_name("toUtf8(_:)")));

/**
 * @note This property has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
@property (readonly) id<SharedApollo_runtimeWsProtocolListener> listener __attribute__((swift_name("listener")));

/**
 * @note This property has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
@property (readonly) id<SharedApollo_runtimeWebSocketConnection> webSocketConnection __attribute__((swift_name("webSocketConnection")));
@end

__attribute__((swift_name("Apollo_runtimeWsProtocolListener")))
@protocol SharedApollo_runtimeWsProtocolListener
@required
- (void)generalErrorPayload:(NSDictionary<NSString *, id> * _Nullable)payload __attribute__((swift_name("generalError(payload:)")));
- (void)networkErrorCause:(SharedKotlinThrowable *)cause __attribute__((swift_name("networkError(cause:)")));
- (void)operationCompleteId:(NSString *)id __attribute__((swift_name("operationComplete(id:)")));
- (void)operationErrorId:(NSString *)id payload:(NSDictionary<NSString *, id> * _Nullable)payload __attribute__((swift_name("operationError(id:payload:)")));
- (void)operationResponseId:(NSString *)id payload:(NSDictionary<NSString *, id> *)payload __attribute__((swift_name("operationResponse(id:payload:)")));
@end

__attribute__((swift_name("KotlinByteIterator")))
@interface SharedKotlinByteIterator : SharedBase <SharedKotlinIterator>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (SharedByte *)next __attribute__((swift_name("next()")));
- (int8_t)nextByte __attribute__((swift_name("nextByte()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("OkioByteString.Companion")))
@interface SharedOkioByteStringCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedOkioByteStringCompanion *shared __attribute__((swift_name("shared")));
- (SharedOkioByteString * _Nullable)decodeBase64:(NSString *)receiver __attribute__((swift_name("decodeBase64(_:)")));
- (SharedOkioByteString *)decodeHex:(NSString *)receiver __attribute__((swift_name("decodeHex(_:)")));
- (SharedOkioByteString *)encodeUtf8:(NSString *)receiver __attribute__((swift_name("encodeUtf8(_:)")));
- (SharedOkioByteString *)ofData:(SharedKotlinByteArray *)data __attribute__((swift_name("of(data:)")));
- (SharedOkioByteString *)toByteString:(NSData *)receiver __attribute__((swift_name("toByteString(_:)")));
- (SharedOkioByteString *)toByteString:(SharedKotlinByteArray *)receiver offset:(int32_t)offset byteCount:(int32_t)byteCount __attribute__((swift_name("toByteString(_:offset:byteCount:)")));
@property (readonly) SharedOkioByteString *EMPTY __attribute__((swift_name("EMPTY")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("OkioBuffer.UnsafeCursor")))
@interface SharedOkioBufferUnsafeCursor : SharedBase <SharedOkioCloseable>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));

/**
 * @note This method converts instances of IOException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (BOOL)closeAndReturnError:(NSError * _Nullable * _Nullable)error __attribute__((swift_name("close_()")));
- (int64_t)expandBufferMinByteCount:(int32_t)minByteCount __attribute__((swift_name("expandBuffer(minByteCount:)")));
- (int32_t)next __attribute__((swift_name("next()")));
- (int64_t)resizeBufferNewSize:(int64_t)newSize __attribute__((swift_name("resizeBuffer(newSize:)")));
- (int32_t)seekOffset:(int64_t)offset __attribute__((swift_name("seek(offset:)")));
@property SharedOkioBuffer * _Nullable buffer __attribute__((swift_name("buffer")));
@property SharedKotlinByteArray * _Nullable data __attribute__((swift_name("data")));
@property int32_t end __attribute__((swift_name("end")));
@property int64_t offset __attribute__((swift_name("offset")));
@property BOOL readWrite __attribute__((swift_name("readWrite")));
@property int32_t start __attribute__((swift_name("start")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("OkioTimeout.Companion")))
@interface SharedOkioTimeoutCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedOkioTimeoutCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedOkioTimeout *NONE __attribute__((swift_name("NONE")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiCustomTypeValueCompanion")))
@interface SharedApollo_apiCustomTypeValueCompanion : SharedBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedApollo_apiCustomTypeValueCompanion *shared __attribute__((swift_name("shared")));

/**
 * @note annotations
 *   kotlin.jvm.JvmStatic
*/
- (SharedApollo_apiCustomTypeValue<id> *)fromRawValueValue:(id _Nullable)value __attribute__((swift_name("fromRawValue(value:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiError.Location")))
@interface SharedApollo_apiErrorLocation : SharedBase
- (instancetype)initWithLine:(int32_t)line column:(int32_t)column __attribute__((swift_name("init(line:column:)"))) __attribute__((objc_designated_initializer));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t column __attribute__((swift_name("column")));
@property (readonly) int32_t line __attribute__((swift_name("line")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiHttpRequest.Builder")))
@interface SharedApollo_apiHttpRequestBuilder : SharedBase
- (instancetype)initWithMethod:(SharedApollo_apiHttpMethod *)method url:(NSString *)url __attribute__((swift_name("init(method:url:)"))) __attribute__((objc_designated_initializer));
- (SharedApollo_apiHttpRequestBuilder *)addHeaderName:(NSString *)name value:(NSString *)value __attribute__((swift_name("addHeader(name:value:)")));
- (SharedApollo_apiHttpRequestBuilder *)addHeadersHeaders:(NSArray<SharedApollo_apiHttpHeader *> *)headers __attribute__((swift_name("addHeaders(headers:)")));
- (SharedApollo_apiHttpRequestBuilder *)bodyBody:(id<SharedApollo_apiHttpBody>)body __attribute__((swift_name("body(body:)")));
- (SharedApollo_apiHttpRequest *)build __attribute__((swift_name("build()")));
- (SharedApollo_apiHttpRequestBuilder *)headersHeaders:(NSArray<SharedApollo_apiHttpHeader *> *)headers __attribute__((swift_name("headers(headers:)")));
@end

__attribute__((swift_name("Apollo_apiHttpBody")))
@protocol SharedApollo_apiHttpBody
@required
- (void)writeToBufferedSink:(id<SharedOkioBufferedSink>)bufferedSink __attribute__((swift_name("writeTo(bufferedSink:)")));
@property (readonly) int64_t contentLength __attribute__((swift_name("contentLength")));
@property (readonly) NSString *contentType __attribute__((swift_name("contentType")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_apiHttpResponse.Builder")))
@interface SharedApollo_apiHttpResponseBuilder : SharedBase
- (instancetype)initWithStatusCode:(int32_t)statusCode __attribute__((swift_name("init(statusCode:)"))) __attribute__((objc_designated_initializer));
- (SharedApollo_apiHttpResponseBuilder *)addHeaderName:(NSString *)name value:(NSString *)value __attribute__((swift_name("addHeader(name:value:)")));
- (SharedApollo_apiHttpResponseBuilder *)addHeadersHeaders:(NSArray<SharedApollo_apiHttpHeader *> *)headers __attribute__((swift_name("addHeaders(headers:)")));
- (SharedApollo_apiHttpResponseBuilder *)bodyBodySource:(id<SharedOkioBufferedSource>)bodySource __attribute__((swift_name("body(bodySource:)")));
- (SharedApollo_apiHttpResponseBuilder *)bodyBodyString:(SharedOkioByteString *)bodyString __attribute__((swift_name("body(bodyString:)"))) __attribute__((deprecated("Use body(BufferedSource) instead")));
- (SharedApollo_apiHttpResponse *)build __attribute__((swift_name("build()")));
- (SharedApollo_apiHttpResponseBuilder *)headersHeaders:(NSArray<SharedApollo_apiHttpHeader *> *)headers __attribute__((swift_name("headers(headers:)")));
@property (readonly) int32_t statusCode __attribute__((swift_name("statusCode")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Apollo_runtimeWsFrameType")))
@interface SharedApollo_runtimeWsFrameType : SharedKotlinEnum<SharedApollo_runtimeWsFrameType *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) SharedApollo_runtimeWsFrameType *text __attribute__((swift_name("text")));
@property (class, readonly) SharedApollo_runtimeWsFrameType *binary __attribute__((swift_name("binary")));
+ (SharedKotlinArray<SharedApollo_runtimeWsFrameType *> *)values __attribute__((swift_name("values()")));
@end

#pragma pop_macro("_Nullable_result")
#pragma clang diagnostic pop
NS_ASSUME_NONNULL_END
