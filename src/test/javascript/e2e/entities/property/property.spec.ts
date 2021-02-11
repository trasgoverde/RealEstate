import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PropertyComponentsPage, PropertyDeleteDialog, PropertyUpdatePage } from './property.page-object';

const expect = chai.expect;

describe('Property e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let propertyComponentsPage: PropertyComponentsPage;
  let propertyUpdatePage: PropertyUpdatePage;
  let propertyDeleteDialog: PropertyDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Properties', async () => {
    await navBarPage.goToEntity('property');
    propertyComponentsPage = new PropertyComponentsPage();
    await browser.wait(ec.visibilityOf(propertyComponentsPage.title), 5000);
    expect(await propertyComponentsPage.getTitle()).to.eq('realEstateApp.property.home.title');
    await browser.wait(ec.or(ec.visibilityOf(propertyComponentsPage.entities), ec.visibilityOf(propertyComponentsPage.noResult)), 1000);
  });

  it('should load create Property page', async () => {
    await propertyComponentsPage.clickOnCreateButton();
    propertyUpdatePage = new PropertyUpdatePage();
    expect(await propertyUpdatePage.getPageTitle()).to.eq('realEstateApp.property.home.createOrEditLabel');
    await propertyUpdatePage.cancel();
  });

  it('should create and save Properties', async () => {
    const nbButtonsBeforeCreate = await propertyComponentsPage.countDeleteButtons();

    await propertyComponentsPage.clickOnCreateButton();

    await promise.all([
      propertyUpdatePage.setNameInput('name'),
      propertyUpdatePage.setPriceInput('5'),
      propertyUpdatePage.setDescriptionInput('description'),
      propertyUpdatePage.buildingTypeSelectLastOption(),
      propertyUpdatePage.serviceTypeSelectLastOption(),
      propertyUpdatePage.setRefInput('ref'),
      propertyUpdatePage.setM2Input('5'),
      propertyUpdatePage.setNumberBedroomInput('5'),
      propertyUpdatePage.setNumberWCInput('5'),
      propertyUpdatePage.locationSelectLastOption(),
      propertyUpdatePage.appuserSelectLastOption(),
    ]);

    expect(await propertyUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await propertyUpdatePage.getPriceInput()).to.eq('5', 'Expected price value to be equals to 5');
    expect(await propertyUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await propertyUpdatePage.getRefInput()).to.eq('ref', 'Expected Ref value to be equals to ref');
    const selectedVisible = propertyUpdatePage.getVisibleInput();
    if (await selectedVisible.isSelected()) {
      await propertyUpdatePage.getVisibleInput().click();
      expect(await propertyUpdatePage.getVisibleInput().isSelected(), 'Expected visible not to be selected').to.be.false;
    } else {
      await propertyUpdatePage.getVisibleInput().click();
      expect(await propertyUpdatePage.getVisibleInput().isSelected(), 'Expected visible to be selected').to.be.true;
    }
    const selectedSold = propertyUpdatePage.getSoldInput();
    if (await selectedSold.isSelected()) {
      await propertyUpdatePage.getSoldInput().click();
      expect(await propertyUpdatePage.getSoldInput().isSelected(), 'Expected sold not to be selected').to.be.false;
    } else {
      await propertyUpdatePage.getSoldInput().click();
      expect(await propertyUpdatePage.getSoldInput().isSelected(), 'Expected sold to be selected').to.be.true;
    }
    const selectedTerrace = propertyUpdatePage.getTerraceInput();
    if (await selectedTerrace.isSelected()) {
      await propertyUpdatePage.getTerraceInput().click();
      expect(await propertyUpdatePage.getTerraceInput().isSelected(), 'Expected terrace not to be selected').to.be.false;
    } else {
      await propertyUpdatePage.getTerraceInput().click();
      expect(await propertyUpdatePage.getTerraceInput().isSelected(), 'Expected terrace to be selected').to.be.true;
    }
    expect(await propertyUpdatePage.getM2Input()).to.eq('5', 'Expected m2 value to be equals to 5');
    expect(await propertyUpdatePage.getNumberBedroomInput()).to.eq('5', 'Expected numberBedroom value to be equals to 5');
    const selectedElevator = propertyUpdatePage.getElevatorInput();
    if (await selectedElevator.isSelected()) {
      await propertyUpdatePage.getElevatorInput().click();
      expect(await propertyUpdatePage.getElevatorInput().isSelected(), 'Expected elevator not to be selected').to.be.false;
    } else {
      await propertyUpdatePage.getElevatorInput().click();
      expect(await propertyUpdatePage.getElevatorInput().isSelected(), 'Expected elevator to be selected').to.be.true;
    }
    const selectedFurnished = propertyUpdatePage.getFurnishedInput();
    if (await selectedFurnished.isSelected()) {
      await propertyUpdatePage.getFurnishedInput().click();
      expect(await propertyUpdatePage.getFurnishedInput().isSelected(), 'Expected furnished not to be selected').to.be.false;
    } else {
      await propertyUpdatePage.getFurnishedInput().click();
      expect(await propertyUpdatePage.getFurnishedInput().isSelected(), 'Expected furnished to be selected').to.be.true;
    }
    const selectedPool = propertyUpdatePage.getPoolInput();
    if (await selectedPool.isSelected()) {
      await propertyUpdatePage.getPoolInput().click();
      expect(await propertyUpdatePage.getPoolInput().isSelected(), 'Expected pool not to be selected').to.be.false;
    } else {
      await propertyUpdatePage.getPoolInput().click();
      expect(await propertyUpdatePage.getPoolInput().isSelected(), 'Expected pool to be selected').to.be.true;
    }
    const selectedGarage = propertyUpdatePage.getGarageInput();
    if (await selectedGarage.isSelected()) {
      await propertyUpdatePage.getGarageInput().click();
      expect(await propertyUpdatePage.getGarageInput().isSelected(), 'Expected garage not to be selected').to.be.false;
    } else {
      await propertyUpdatePage.getGarageInput().click();
      expect(await propertyUpdatePage.getGarageInput().isSelected(), 'Expected garage to be selected').to.be.true;
    }
    expect(await propertyUpdatePage.getNumberWCInput()).to.eq('5', 'Expected numberWC value to be equals to 5');
    const selectedAc = propertyUpdatePage.getAcInput();
    if (await selectedAc.isSelected()) {
      await propertyUpdatePage.getAcInput().click();
      expect(await propertyUpdatePage.getAcInput().isSelected(), 'Expected ac not to be selected').to.be.false;
    } else {
      await propertyUpdatePage.getAcInput().click();
      expect(await propertyUpdatePage.getAcInput().isSelected(), 'Expected ac to be selected').to.be.true;
    }

    await propertyUpdatePage.save();
    expect(await propertyUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await propertyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Property', async () => {
    const nbButtonsBeforeDelete = await propertyComponentsPage.countDeleteButtons();
    await propertyComponentsPage.clickOnLastDeleteButton();

    propertyDeleteDialog = new PropertyDeleteDialog();
    expect(await propertyDeleteDialog.getDialogTitle()).to.eq('realEstateApp.property.delete.question');
    await propertyDeleteDialog.clickOnConfirmButton();

    expect(await propertyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
