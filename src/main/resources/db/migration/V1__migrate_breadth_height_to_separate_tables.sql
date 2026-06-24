-- Migration: Move breadth1-35 and height1-35 from rcc_form_work to separate tables
-- breadth and height stay in rcc_form_work; only breadth1-35 and height1-35 go to separate tables
-- Run this AFTER the application has started once (Hibernate creates the new tables)
-- Backup your database before running!

-- Step 1: Migrate existing breadth1-35 data to rcc_form_work_breadth
INSERT INTO rcc_form_work_breadth (rcc_form_work_id, breadth1, breadth2, breadth3, breadth4, breadth5, breadth6, breadth7, breadth8, breadth9, breadth10, breadth11, breadth12, breadth13, breadth14, breadth15, breadth16, breadth17, breadth18, breadth19, breadth20, breadth21, breadth22, breadth23, breadth24, breadth25, breadth26, breadth27, breadth28, breadth29, breadth30, breadth31, breadth32, breadth33, breadth34, breadth35)
SELECT id, breadth1, breadth2, breadth3, breadth4, breadth5, breadth6, breadth7, breadth8, breadth9, breadth10, breadth11, breadth12, breadth13, breadth14, breadth15, breadth16, breadth17, breadth18, breadth19, breadth20, breadth21, breadth22, breadth23, breadth24, breadth25, breadth26, breadth27, breadth28, breadth29, breadth30, breadth31, breadth32, breadth33, breadth34, breadth35
FROM rcc_form_work
WHERE id NOT IN (SELECT rcc_form_work_id FROM rcc_form_work_breadth WHERE rcc_form_work_id IS NOT NULL)
  AND (breadth1 IS NOT NULL OR breadth2 IS NOT NULL OR breadth3 IS NOT NULL);

-- Step 2: Migrate existing height1-35 data to rcc_form_work_height
INSERT INTO rcc_form_work_height (rcc_form_work_id, height1, height2, height3, height4, height5, height6, height7, height8, height9, height10, height11, height12, height13, height14, height15, height16, height17, height18, height19, height20, height21, height22, height23, height24, height25, height26, height27, height28, height29, height30, height31, height32, height33, height34, height35)
SELECT id, height1, height2, height3, height4, height5, height6, height7, height8, height9, height10, height11, height12, height13, height14, height15, height16, height17, height18, height19, height20, height21, height22, height23, height24, height25, height26, height27, height28, height29, height30, height31, height32, height33, height34, height35
FROM rcc_form_work
WHERE id NOT IN (SELECT rcc_form_work_id FROM rcc_form_work_height WHERE rcc_form_work_id IS NOT NULL)
  AND (height1 IS NOT NULL OR height2 IS NOT NULL OR height3 IS NOT NULL);

-- Step 3: Drop breadth1-35 and height1-35 columns from rcc_form_work (breadth and height stay)
ALTER TABLE rcc_form_work
  DROP COLUMN breadth1, DROP COLUMN breadth2, DROP COLUMN breadth3, DROP COLUMN breadth4, DROP COLUMN breadth5, DROP COLUMN breadth6, DROP COLUMN breadth7, DROP COLUMN breadth8, DROP COLUMN breadth9, DROP COLUMN breadth10,
  DROP COLUMN breadth11, DROP COLUMN breadth12, DROP COLUMN breadth13, DROP COLUMN breadth14, DROP COLUMN breadth15, DROP COLUMN breadth16, DROP COLUMN breadth17, DROP COLUMN breadth18, DROP COLUMN breadth19, DROP COLUMN breadth20,
  DROP COLUMN breadth21, DROP COLUMN breadth22, DROP COLUMN breadth23, DROP COLUMN breadth24, DROP COLUMN breadth25, DROP COLUMN breadth26, DROP COLUMN breadth27, DROP COLUMN breadth28, DROP COLUMN breadth29, DROP COLUMN breadth30,
  DROP COLUMN breadth31, DROP COLUMN breadth32, DROP COLUMN breadth33, DROP COLUMN breadth34, DROP COLUMN breadth35,
  DROP COLUMN height1, DROP COLUMN height2, DROP COLUMN height3, DROP COLUMN height4, DROP COLUMN height5, DROP COLUMN height6, DROP COLUMN height7, DROP COLUMN height8, DROP COLUMN height9, DROP COLUMN height10,
  DROP COLUMN height11, DROP COLUMN height12, DROP COLUMN height13, DROP COLUMN height14, DROP COLUMN height15, DROP COLUMN height16, DROP COLUMN height17, DROP COLUMN height18, DROP COLUMN height19, DROP COLUMN height20,
  DROP COLUMN height21, DROP COLUMN height22, DROP COLUMN height23, DROP COLUMN height24, DROP COLUMN height25, DROP COLUMN height26, DROP COLUMN height27, DROP COLUMN height28, DROP COLUMN height29, DROP COLUMN height30,
  DROP COLUMN height31, DROP COLUMN height32, DROP COLUMN height33, DROP COLUMN height34, DROP COLUMN height35;
