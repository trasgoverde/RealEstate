import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LocationComponentsPage, LocationDeleteDialog, LocationUpdatePage } from './location.page-object';

const expect = chai.expect;

describe('Location e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let locationComponentsPage: LocationComponentsPage;
  let locationUpdatePage: LocationUpdatePage;
  let locationDeleteDialog: LocationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Locations', async () => {
    await navBarPage.goToEntity('location');
    locationComponentsPage = new LocationComponentsPage();
    await browser.wait(ec.visibilityOf(locationComponentsPage.title), 5000);
    expect(await locationComponentsPage.getTitle()).to.eq('realEstateApp.location.home.title');
    await browser.wait(ec.or(ec.visibilityOf(locationComponentsPage.entities), ec.visibilityOf(locationComponentsPage.noResult)), 1000);
  });

  it('should load create Location page', async () => {
    await locationComponentsPage.clickOnCreateButton();
    locationUpdatePage = new LocationUpdatePage();
    expect(await locationUpdatePage.getPageTitle()).to.eq('realEstateApp.location.home.createOrEditLabel');
    await locationUpdatePage.cancel();
  });

  it('should create and save Locations', async () => {
    const nbButtonsBeforeCreate = await locationComponentsPage.countDeleteButtons();

    await locationComponentsPage.clickOnCreateButton();

    await promise.all([
      locationUpdatePage.setRefInput('ref'),
      locationUpdatePage.setProvinceInput('province'),
      locationUpdatePage.setTownInput('town'),
      locationUpdatePage.setCpInput('cp'),
      locationUpdatePage.typeOfRoadSelectLastOption(),
      locationUpdatePage.setNameRoadInput('nameRoad'),
      locationUpdatePage.setNumberInput('5'),
      locationUpdatePage.setApartmentInput('5'),
      locationUpdatePage.setBuildingInput('5'),
      locationUpdatePage.setDoorInput('5'),
      locationUpdatePage.setStairInput('stair'),
      locationUpdatePage.setUrlgmapsInput('urlgmaps'),
      locationUpdatePage.setLatitudeInput('5'),
      locationUpdatePage.setLongitudeInput('5'),
    ]);

    expect(await locationUpdatePage.getRefInput()).to.eq('ref', 'Expected Ref value to be equals to ref');
    expect(await locationUpdatePage.getProvinceInput()).to.eq('province', 'Expected Province value to be equals to province');
    expect(await locationUpdatePage.getTownInput()).to.eq('town', 'Expected Town value to be equals to town');
    expect(await locationUpdatePage.getCpInput()).to.eq('cp', 'Expected Cp value to be equals to cp');
    expect(await locationUpdatePage.getNameRoadInput()).to.eq('nameRoad', 'Expected NameRoad value to be equals to nameRoad');
    expect(await locationUpdatePage.getNumberInput()).to.eq('5', 'Expected number value to be equals to 5');
    expect(await locationUpdatePage.getApartmentInput()).to.eq('5', 'Expected apartment value to be equals to 5');
    expect(await locationUpdatePage.getBuildingInput()).to.eq('5', 'Expected building value to be equals to 5');
    expect(await locationUpdatePage.getDoorInput()).to.eq('5', 'Expected door value to be equals to 5');
    expect(await locationUpdatePage.getStairInput()).to.eq('stair', 'Expected Stair value to be equals to stair');
    expect(await locationUpdatePage.getUrlgmapsInput()).to.eq('urlgmaps', 'Expected Urlgmaps value to be equals to urlgmaps');
    expect(await locationUpdatePage.getLatitudeInput()).to.eq('5', 'Expected latitude value to be equals to 5');
    expect(await locationUpdatePage.getLongitudeInput()).to.eq('5', 'Expected longitude value to be equals to 5');

    await locationUpdatePage.save();
    expect(await locationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await locationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Location', async () => {
    const nbButtonsBeforeDelete = await locationComponentsPage.countDeleteButtons();
    await locationComponentsPage.clickOnLastDeleteButton();

    locationDeleteDialog = new LocationDeleteDialog();
    expect(await locationDeleteDialog.getDialogTitle()).to.eq('realEstateApp.location.delete.question');
    await locationDeleteDialog.clickOnConfirmButton();

    expect(await locationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
