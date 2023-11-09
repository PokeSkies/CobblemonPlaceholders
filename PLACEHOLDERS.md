# Player Info
None? Any ideas?

# Player Party Info
`<[placeholder]:[slot]>`

Slot is 1-6

| Placeholder                                            | Return Type                |
| ------------------------------------------------------ | -------------------------- |
| <cobblemon_party_species:[slot]>                       | string                     |
| <cobblemon_party_species:[slot]:[species_placeholder]> | depends on placeholder     |
| <cobblemon_party_nickname:[slot]>                      | string                     |
| <cobblemon_party_form:[slot]>                          | string                     |
| <cobblemon_party_level:[slot]>                         | int                        |
| <cobblemon_party_exp:[slot]>                           | int                        |
| <cobblemon_party_shiny:[slot]>                         | boolean                    |
| <cobblemon_party_helditem:[slot]>                      | resource location          |
| <cobblemon_party_friendship:[slot]>                    | int                        |
| <cobblemon_party_ability:[slot]>                       | string                     |
| <cobblemon_party_nature:[slot]>                        | string                     |
| <cobblemon_party_hiddenpower:[slot]>                   | boolean                    |
| <cobblemon_party_gender:[slot]>                        | string                     |
| <cobblemon_party_caughtball:[slot]>                    | string                     |
| <cobblemon_party_dmax:[slot]>                          | int                        |
| <cobblemon_party_gmax:[slot]>                          | boolean                    |
| <cobblemon_party_teratype:[slot]>                      | string                     |
| <cobblemon_party_tradable:[slot]>                      | boolean                    |
| <cobblemon_party_moveset:[slot]>                       | list of strings            |
| <cobblemon_party_moveset:[slot]:[1-4]>                 | string                     |
| <cobblemon_party_stats_hp:[slot]>                      | int                        |
| <cobblemon_party_stats_atk:[slot]>                     | int                        |
| <cobblemon_party_stats_def:[slot]>                     | int                        |
| <cobblemon_party_stats_spa:[slot]>                     | int                        |
| <cobblemon_party_stats_spd:[slot]>                     | int                        |
| <cobblemon_party_stats_spe:[slot]>                     | int                        |
| <cobblemon_party_stats_ivs_hp:[slot]>                  | int                        |
| <cobblemon_party_stats_ivs_atk:[slot]>                 | int                        |
| <cobblemon_party_stats_ivs_def:[slot]>                 | int                        |
| <cobblemon_party_stats_ivs_spa:[slot]>                 | int                        |
| <cobblemon_party_stats_ivs_spd:[slot]>                 | int                        |
| <cobblemon_party_stats_ivs_spe:[slot]>                 | int                        |
| <cobblemon_party_stats_ivs_total:[slot]>               | int                        |
| <cobblemon_party_stats_ivs_percent:[slot]>             | int                        |
| <cobblemon_party_stats_evs_hp:[slot]>                  | int                        |
| <cobblemon_party_stats_evs_atk:[slot]>                 | int                        |
| <cobblemon_party_stats_evs_def:[slot]>                 | int                        |
| <cobblemon_party_stats_evs_spa:[slot]>                 | int                        |
| <cobblemon_party_stats_evs_spd:[slot]>                 | int                        |
| <cobblemon_party_stats_evs_spe:[slot]>                 | int                        |
| <cobblemon_party_stats_evs_total:[slot]>               | int                        |
| <cobblemon_party_stats_evs_percent:[slot]>             | int                        |
| <cobblemon_party_aspects:[slot]>                       | list of strings            |
| <cobblemon_party_aspect:[slot]:[aspect]>               | result (boolean or choice) |
| <cobblemon_party_aspect_has:[slot]:[aspect]>           | boolean                    |

#### Possibilities:
- health

# Species Info
`<[placeholder]:[species]>`

| Placeholder                                   | Return Type     |
| --------------------------------------------- | --------------- |
| <cobblemon_species_name:[species]>            | string          |
| <cobblemon_species_id_national:[species]>     | int             |
| <cobblemon_species_types:[species]>           | list of strings |
| <cobblemon_species_type:[species]:[1-2]>      | string          |
| <cobblemon_species_catchrate:[species]>       | int             |
| <cobblemon_species_abilities:[species]>       | list of strings |
| <cobblemon_species_ability:[species]:[slot]>  | string          |
| <cobblemon_species_moves:[species]>           | list of strings |
| <cobblemon_species_evolutions:[species]>      | list of strings |
| <cobblemon_species_evolutions_pre:[species]>  | string          |
| <cobblemon_species_evolutions_post:[species]> | string          |
| <cobblemon_species_egggroups:[species]>       | string          |
| <cobblemon_species_maleratio:[species]>       | float           |
| <cobblemon_species_basestats_hp:[species]>    | string          |
| <cobblemon_species_basestats_atk:[species]>   | string          |
| <cobblemon_species_basestats_def:[species]>   | string          |
| <cobblemon_species_basestats_spa:[species]>   | string          |
| <cobblemon_species_basestats_spd:[species]>   | string          |
| <cobblemon_species_basestats_spe:[species]>   | string          |

#### Possibilities:
- drops?
- forms?
- baseexperienceyield?
- basefriendship?
- evyield?