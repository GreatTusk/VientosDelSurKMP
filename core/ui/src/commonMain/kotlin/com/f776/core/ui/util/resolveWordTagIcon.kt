package com.f776.core.ui.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.automirrored.filled.ShortText
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*

fun resolveWordTagIcon(wordTagId: Int) =
    when (wordTagId) {
        // The top-level categories (1-11) weren't in the CSV, so leaving them as-is
        1 -> Icons.Default.Science               // Natural Sciences
        2 -> Icons.Default.LocalHospital         // Medical and Health Sciences
        3 -> Icons.Default.People                // Social Sciences and Humanities
        4 -> Icons.Default.SportsBasketball      // Sports and Physical Activities
        5 -> Icons.Default.Palette               // Arts and Cultural Studies
        6 -> Icons.Default.Build                 // Technology and Engineering
        7 -> Icons.Default.SportsEsports         // Games and Leisure
        8 -> Icons.Default.Work                  // Applied Disciplines
        9 -> Icons.Default.Functions             // Mathematical and Logical Fields
        10 -> Icons.AutoMirrored.Filled.Chat     // Media and Communication
        11 -> Icons.Default.Category             // Miscellaneous

        // Categories 12-30 weren't in the CSV, so leaving them as-is
        12 -> Icons.Default.RecordVoiceOver      // Language Politeness Levels
        13 -> Icons.Default.Group                // Gender and Social Language Markers
        14 -> Icons.Default.TextFields           // Usage and Register
        15 -> Icons.Default.Badge                // Naming and Identity
        16 -> Icons.Default.Spellcheck           // Linguistic Features
        17 -> Icons.Default.AutoStories          // Cultural and Mythological
        18 -> Icons.Default.TextFormat           // Language Tone and Style
        19 -> Icons.Default.Link                 // Referential Categories
        20 -> Icons.Default.Book                 // Specialized and Rare Terminology
        21 -> Icons.Default.PlayArrow            // Verb Types
        22 -> Icons.Default.History              // Archaic Verb Types
        23 -> Icons.AutoMirrored.Filled.Label    // Noun Types
        24 -> Icons.Default.FormatColorText      // Adjective Types
        25 -> Icons.Default.Compare              // Modifying and Connecting Words
        26 -> Icons.AutoMirrored.Filled.Help     // Auxiliary and Supporting Words
        27 -> Icons.Default.Style                // Descriptive and Qualifying Words
        28 -> Icons.Default.Navigation           // Reference and Indicator Words
        29 -> Icons.Default.Edit                 // Grammatical Modifiers
        30 -> Icons.Default.Language             // Dialects

        // Name and Organization related - All match CSV
        184 -> Icons.Default.DirectionsBoat      // Ship name
        204 -> Icons.Default.Business            // Company name
        218 -> Icons.Default.Palette             // Work of art, literature, music, etc. name
        221 -> Icons.Default.Place               // Place name
        222 -> Icons.Default.Inventory           // Product name
        223 -> Icons.Default.Groups              // Organization name
        230 -> Icons.Default.Person              // Full name of a particular person
        220 -> Icons.Default.People              // Family or surname
        200 -> Icons.Default.Badge               // Given name or forename, gender not specified

        // Language Register and Style - All match CSV
        191 -> Icons.Default.EmojiEvents         // Honorific or respectful (sonkeigo) language
        192 -> Icons.Default.ThumbUp             // Polite (teineigo) language
        211 -> Icons.Default.SentimentSatisfied  // Humble (kenjougo) language
        209 -> Icons.Default.School              // Formal or literary term
        225 -> Icons.AutoMirrored.Filled.Chat    // Colloquial
        214 -> Icons.Default.Forum               // Familiar language
        201 -> Icons.Default.ChildCare           // Children's language
        190 -> Icons.Default.Male                // Male term or language
        227 -> Icons.Default.Female              // Female term or language

        // Special Language Categories - All match CSV
        205 -> Icons.Default.AutoStories         // Poetical term
        206 -> Icons.AutoMirrored.Filled.Help    // Unclassified name
        207 -> Icons.Default.RecordVoiceOver     // Onomatopoeic or mimetic word
        208 -> Icons.Default.FormatQuote         // Yojijukugo
        203 -> Icons.Default.Lightbulb           // Proverb
        216 -> Icons.Default.Translate           // Idiomatic expression
        188 -> Icons.AutoMirrored.Filled.ShortText // Abbreviation

        // Dialects - All match CSV
        234 -> Icons.Default.Language            // Brazilian
        235 -> Icons.Default.Language            // Kantou-ben
        236 -> Icons.Default.Language            // Hokkaido-ben
        237 -> Icons.Default.Language            // Touhoku-ben
        238 -> Icons.Default.Language            // Kyoto-ben
        239 -> Icons.Default.Language            // Kyuushuu-ben
        240 -> Icons.Default.Language            // Osaka-ben
        241 -> Icons.Default.Language            // Nagano-ben
        242 -> Icons.Default.Language            // Ryuukyuu-ben
        243 -> Icons.Default.Language            // Tsugaru-ben
        244 -> Icons.Default.Language            // Tosa-ben
        245 -> Icons.Default.Language            // Kansai-ben

        // Temporal Classifications - All match CSV
        196 -> Icons.Default.History             // Archaic
        212 -> Icons.Default.Update              // Obsolete term
        213 -> Icons.Default.Timeline            // Historical term
        232 -> Icons.Default.Schedule            // Dated term

        // Special Usage - All match CSV
        194 -> Icons.AutoMirrored.Filled.Message // Slang
        233 -> Icons.Default.Language            // Internet slang
        226 -> Icons.AutoMirrored.Filled.MenuBook // Manga slang
        193 -> Icons.Default.Block               // Vulgar expression or word
        187 -> Icons.Default.ThumbDown           // Derogatory
        197 -> Icons.Default.SwapHoriz           // Euphemistic
        231 -> Icons.Default.TagFaces            // Jocular, humorous term
        229 -> Icons.Default.Warning             // Sensitive

        // Cultural/Religious - All match CSV
        183 -> Icons.Default.Brightness7         // Deity
        185 -> Icons.Default.AutoStories         // Legend
        215 -> Icons.Default.AutoStories         // Mythology
        292 -> Icons.Default.Church              // Christianity

        // Documentation/References - All match CSV
        186 -> Icons.Default.FormatQuote         // Quotation
        219 -> Icons.Default.Description         // Document
        189 -> Icons.Default.Handyman            // Service

        // Miscellaneous - All match CSV
        195 -> Icons.Default.Book                // Fiction
        210 -> Icons.Default.Category            // Object
        202 -> Icons.Default.Event               // Event
        217 -> Icons.Default.Pets                // Creature
        224 -> Icons.Default.TheaterComedy       // Character
        228 -> Icons.Default.Group               // Group
        133 -> Icons.AutoMirrored.Filled.Help    // Unclassified

        // Specific fields (246-341) - All comments updated to match CSV
        246 -> Icons.Default.MusicNote           // Music
        247 -> Icons.Default.Stars               // Astronomy
        248 -> Icons.Default.MedicalServices     // Surgery
        249 -> Icons.Default.SportsCricket       // Baseball
        250 -> Icons.Default.Biotech             // Genetics
        251 -> Icons.Default.Public              // Geography
        252 -> Icons.Default.LocalHospital       // Dentistry
        253 -> Icons.Default.Speed               // Horse racing
        254 -> Icons.Default.Pets                // Ornithology
        255 -> Icons.Default.DirectionsCar       // Motorsport
        256 -> Icons.Default.SportsEsports       // Video games
        257 -> Icons.Default.Science             // Biology
        258 -> Icons.Default.Security            // Military
        259 -> Icons.Default.SportsSoccer        // Golf
        260 -> Icons.Default.Diamond             // Mineralogy
        261 -> Icons.Default.Festival            // Japanese mythology
        262 -> Icons.Default.Palette             // Art, aesthetics
        263 -> Icons.Default.Analytics           // Statistics
        264 -> Icons.Default.ViewModule          // Crystallography
        265 -> Icons.Default.Healing             // Pathology
        266 -> Icons.Default.Camera              // Photography
        267 -> Icons.Default.Restaurant          // Food, cooking
        268 -> Icons.Default.Pool                // Fishing
        269 -> Icons.Default.Business            // Business
        270 -> Icons.Default.Tv                  // Television
        271 -> Icons.Default.BabyChangingStation // Embryology
        272 -> Icons.Default.Casino              // Hanafuda
        273 -> Icons.Default.IceSkating          // Figure skating
        274 -> Icons.Default.Grass               // Agriculture
        275 -> Icons.Default.Biotech             // Physiology
        276 -> Icons.Default.AccountBalance      // Greek mythology
        277 -> Icons.Default.ElectricBolt        // Electronics
        278 -> Icons.Default.Park                // Gardening, horticulture
        279 -> Icons.Default.Language            // Internet
        280 -> Icons.Default.Style               // Card games
        281 -> Icons.AutoMirrored.Filled.TrendingUp // Stock market
        282 -> Icons.Default.TheaterComedy       // Noh
        283 -> Icons.Default.AccountBalance      // Economics
        284 -> Icons.Default.Castle              // Roman mythology
        285 -> Icons.Default.EnergySavingsLeaf   // Ecology
        286 -> Icons.Default.Psychology          // Psychiatry
        287 -> Icons.Default.Museum              // Paleontology
        288 -> Icons.Default.Architecture        // Civil engineering
        289 -> Icons.Default.GridOn              // Go (game)
        290 -> Icons.Default.BugReport           // Entomology
        291 -> Icons.Default.Print               // Printing
        293 -> Icons.Default.Terrain             // Geology
        294 -> Icons.Default.Square              // Geometry
        295 -> Icons.Default.AccessibilityNew    // Anatomy
        296 -> Icons.Default.DownhillSkiing      // Skiing
        297 -> Icons.Default.Spellcheck          // Grammar
        298 -> Icons.Default.Psychology          // Psychoanalysis
        299 -> Icons.Default.Computer            // Computing
        300 -> Icons.Default.Psychology          // Philosophy
        301 -> Icons.Default.Calculate           // Mathematics
        302 -> Icons.Default.Medication          // Pharmacology
        303 -> Icons.Default.VideoLibrary        // Audiovisual
        304 -> Icons.Default.SelfImprovement     // Buddhism
        305 -> Icons.Default.Science             // Biochemistry
        306 -> Icons.Default.Science             // Physics
        307 -> Icons.Default.TheaterComedy       // Kabuki
        308 -> Icons.Default.SportsKabaddi       // Professional wrestling
        309 -> Icons.Default.Router              // Telecommunications
        310 -> Icons.Default.Build               // Engineering
        311 -> Icons.Default.Code                // Logic
        312 -> Icons.Default.Museum              // Archeology
        313 -> Icons.Default.Flight              // Aviation
        314 -> Icons.Default.SportsKabaddi       // Martial arts
        315 -> Icons.Default.AccountBalance      // Finance
        316 -> Icons.AutoMirrored.Filled.MenuBook // Manga
        317 -> Icons.Default.Casino              // Shogi
        318 -> Icons.Default.Gavel               // Law
        319 -> Icons.Default.Pets                // Veterinary terms
        320 -> Icons.Default.Casino              // Mahjong
        321 -> Icons.Default.Train               // Railway
        322 -> Icons.Default.ElectricBolt        // Electricity, elec. eng.
        323 -> Icons.Default.Movie               // Film
        324 -> Icons.Default.Construction        // Mining
        325 -> Icons.Default.SportsKabaddi       // Sumo
        326 -> Icons.Default.SportsKabaddi       // Boxing
        327 -> Icons.Default.Checkroom           // Clothing
        328 -> Icons.Default.AccountBalance      // Politics
        329 -> Icons.Default.Translate           // Linguistics
        330 -> Icons.Default.Park                // Botany
        331 -> Icons.Default.LocalHospital       // Medicine
        332 -> Icons.Default.Build               // Mechanical engineering
        333 -> Icons.Default.Festival            // Shinto
        334 -> Icons.Default.AutoStories         // Chinese mythology
        335 -> Icons.Default.Psychology          // Psychology
        336 -> Icons.Default.Cloud               // Meteorology
        337 -> Icons.Default.Science             // Chemistry
        338 -> Icons.Default.SportsBasketball    // Sports
        339 -> Icons.Default.Pets                // Zoology
        340 -> Icons.Default.Verified            // Trademark
        341 -> Icons.Default.Architecture        // Architecture

        else -> Icons.Default.Home               // Fallback icon for unknown IDs
    }